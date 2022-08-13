package com.iwebui.business;



import com.iwebui.base.BaseTest;
import com.iwebui.handle.LanQiaoSampleHandle;
import com.iwebui.handle.ReloginWebHandle;
import com.iwebui.utils.LoadStaticConfigUtil;
import org.testng.annotations.Test;


/**
 * 测试用例业务层
 * @author czy
 */
@Test
public class LoginTestCase extends BaseTest {
    //admin登录页
    String adminUrl = (String) LoadStaticConfigUtil.getCommonYml( "url.adminUrl");
    //首页登录+注册url
    String loginUrl = (String) LoadStaticConfigUtil.getCommonYml( "url.loginUrl");
    private LanQiaoSampleHandle sampleHandle;
    private ReloginWebHandle reloginWebHandle;
    //登录admin被测网页
    public void adminWeb()  {
        //初始化被测页面
        sampleHandle = new LanQiaoSampleHandle(driver);
        //进入登录页面
        sampleHandle.searchPage(adminUrl);
        //开始输入账号密码登录
        sampleHandle.enterPage();
    }

    //注册网页
    public void reloginWeb()  {
        //初始化被测页面
        reloginWebHandle = new ReloginWebHandle(driver);
        //进入登录页面
        reloginWebHandle.searchPage(loginUrl);
        //开始输入账号密码登录验证码注册
        reloginWebHandle.enterPage();
    }

    //注册后登录
    public void loginWeb()  {
        //初始化被测页面
        reloginWebHandle = new ReloginWebHandle(driver);
        //进入登录页面
        reloginWebHandle.searchPage(loginUrl);
        //登录
        reloginWebHandle.loginPage();
    }

    //Excel数据驱动
    public void dataDrive(){
        //初始化被测页面
        reloginWebHandle = new ReloginWebHandle(driver);
        //进入登录页面
        reloginWebHandle.searchPage(loginUrl);
        //数据驱动依次驱动excel数据注册
        reloginWebHandle.dataDrive();
    }
}
