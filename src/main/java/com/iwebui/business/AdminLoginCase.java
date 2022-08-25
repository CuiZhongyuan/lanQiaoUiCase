package com.iwebui.business;

import com.iwebui.base.BaseTest;
import com.iwebui.handle.LanQiaoSampleHandle;
import com.iwebui.handle.ReloginWebHandle;
import com.iwebui.utils.LoadStaticConfigUtil;

public class AdminLoginCase extends BaseTest {
    //admin登录页
    String adminUrl = (String) LoadStaticConfigUtil.getCommonYml( "url.adminUrl");
    private LanQiaoSampleHandle sampleHandle;
    private ReloginWebHandle reloginWebHandle;
    //登录admin被测网页
    public void adminWebTest()  {
        //初始化被测页面
        sampleHandle = new LanQiaoSampleHandle(driver);
        //进入登录页面
        sampleHandle.searchPage(adminUrl);
        //开始输入账号密码登录
        sampleHandle.enterPage();
    }
}
