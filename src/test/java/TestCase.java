import com.iwebui.dto.InventoryEntity;
import com.iwebui.utils.*;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class TestCase {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void cases() {
        String excelPath = (String) LoadStaticConfigUtil.getCommonYml("testcaseexcel.path");
        List<InventoryEntity> entityList = EasyPoiUtil.importExcel(excelPath, 1, 1, InventoryEntity.class);
        //写入验证码code
        for (InventoryEntity inventoryCode : entityList) {
            inventoryCode.setVerifyCode("122112ft");
        }
        ExcelTestResultOutputUtil.exportSheet(entityList);
    }

    @Test
    public void getpic() throws IOException {
        try {
            HttpGet httpGet = new HttpGet("https://9531ab83900c.hz-iframe-svc.simplelab.cn/goods/VerifyCodeServlet?1660534988855");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                InputStream inStream = entity.getContent();
                FileOutputStream fw = new FileOutputStream("D:\\picture\\" + System.currentTimeMillis() + ".jfif", false);
                int b = inStream.read();
                while (b != -1) {
                    fw.write(b);
                    b = inStream.read();
                }
                fw.close();
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
    }

    @Test
    public void getcode() throws IOException {
        String ocrUrl = "http://127.0.0.1:5000/getOcr";
        String  codeUrl = (String) LoadStaticConfigUtil.getCommonYml( "url.codeUrl");
        String getPath = ocrUrl+"?path="+GetOcrUtil.getPath(codeUrl);
        String getCode = RestTemplateUtils.get(getPath,String.class).getBody();
        System.out.println(getCode);
    }
}
