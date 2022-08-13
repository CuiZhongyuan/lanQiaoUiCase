package com.iwebui.handle;

import com.iwebui.base.BaseBrowser;
import com.iwebui.page.data.LanQiaoSampleData;
import com.iwebui.page.element.LanQiaoSampleElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

@Slf4j
public class LanQiaoSampleHandle extends BaseBrowser {
    /**
     * 构造器 1
     *
     * @param driver 驱动
     */
    public LanQiaoSampleHandle(WebDriver driver) {
        super(driver);
    }

    /**
     * 进入被测页面
     */
    public void searchPage(String url) {
        log.info("跳转进入被测页面");
        super.enterPage(url);
    }

    /**
     * 定位输入框输入账号密码登录
     */
    public void enterPage(){
        try {
            log.info("开始登录");
            sendInput(LanQiaoSampleElement.LOGIN, LanQiaoSampleData.TEXT_NAME);
            sendInput(LanQiaoSampleElement.PWD, LanQiaoSampleData.TEXT_PWD);
            clickButton(LanQiaoSampleElement.BUTTON);
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
