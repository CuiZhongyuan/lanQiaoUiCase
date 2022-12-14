package com.iwebui.handle;

import com.iwebui.base.BaseBrowser;
import com.iwebui.dto.InventoryEntity;
import com.iwebui.page.data.LanQiaoSampleData;
import com.iwebui.page.element.LanQiaoSampleElement;
import com.iwebui.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
public class ReloginWebHandle extends BaseBrowser {
    /**
     * 构造器 1
     *
     * @param driver 驱动
     */
    public ReloginWebHandle(WebDriver driver) {
        super(driver);
    }

    /**
     * 进入注册页面
     */
    public void searchPage(String loginUrl) {
        log.info("跳转进入注册页面");
        super.enterPage(loginUrl);
    }

    /**
     * 定位输入框输入账号密码登录
     */
    public void regEnterPage(){
        try {
            log.info("开始注册");
            driver.switchTo().frame(0);
            clickButton(LanQiaoSampleElement.relogin);
            sleep(1000);
            sendInput(LanQiaoSampleElement.loginname, LanQiaoSampleData.loginname);
            sendInput(LanQiaoSampleElement.loginpass, LanQiaoSampleData.loginpass);
            sendInput(LanQiaoSampleElement.reloginpass, LanQiaoSampleData.reloginpass);
            sendInput(LanQiaoSampleElement.email, LanQiaoSampleData.email);
            sendInput(LanQiaoSampleElement.verifyCode, getCode());
            clickButton(LanQiaoSampleElement.submitBtn);
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void regLoginPage() {
        log.info("开始注册登录");
        try {
            driver.switchTo().frame(0);
            clickButton(LanQiaoSampleElement.pre);
            driver.switchTo().defaultContent();
            clickButton(LanQiaoSampleElement.linkTextLogin);
            sendInput(LanQiaoSampleElement.name, LanQiaoSampleData.name);
            sendInput(LanQiaoSampleElement.pass, LanQiaoSampleData.pass);
            sendInput(LanQiaoSampleElement.code, getCode());
            sendInput(LanQiaoSampleElement.code, getCode());
            clickButton(LanQiaoSampleElement.btn);
            sleep(1000);
            driver.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void loginPage() {
        try {
            log.info("开始登录");
            driver.switchTo().frame(0);
            clickButton(LanQiaoSampleElement.pre);
            driver.switchTo().defaultContent();
            clickButton(LanQiaoSampleElement.linkTextLogin);
            sendInput(LanQiaoSampleElement.name, LanQiaoSampleData.name);
            sendInput(LanQiaoSampleElement.pass, LanQiaoSampleData.pass);
            sendInput(LanQiaoSampleElement.code, getCode());
            clickButton(LanQiaoSampleElement.btn);
            sleep(1000);
//            driver.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * excel数据驱动
     */
    public void dataDrive() {
        String excelPath = (String) LoadStaticConfigUtil.getCommonYml( "testcaseexcel.path");
        List<InventoryEntity> entityList = EasyPoiUtil.importExcel(excelPath,1,1,InventoryEntity.class);
        //获取OCR识别结果写入excel验证码code列中
        for (InventoryEntity inventoryCode : entityList){
            inventoryCode.setVerifyCode(getCode());
        }
        ExcelTestResultOutputUtil.exportSheet(entityList);
        //读取excel数据以此注册
        for (InventoryEntity inventory : entityList){
            clickButton(LanQiaoSampleElement.relogin);
            try {
                sleep(1000);
                sendInput(LanQiaoSampleElement.loginname, inventory.getLoginname());
                sendInput(LanQiaoSampleElement.loginpass, inventory.getLoginpass());
                sendInput(LanQiaoSampleElement.reloginpass, inventory.getReloginpass());
                sendInput(LanQiaoSampleElement.email, inventory.getEmail());
                sendInput(LanQiaoSampleElement.verifyCode, inventory.getVerifyCode());
                clickButton(LanQiaoSampleElement.submitBtn);
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 获取验证码-存在误差
     */
    public String getCode() {
        String ocrUrl = "http://127.0.0.1:5000/getOcr";
        String  codeUrl = (String) LoadStaticConfigUtil.getCommonYml( "url.codeUrl");
        String getPath = null;
        try {
            getPath = ocrUrl+"?path="+ GetOcrUtil.getPath(codeUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String getCode = RestTemplateUtils.get(getPath,String.class).getBody();
        return getCode;
    }
}
