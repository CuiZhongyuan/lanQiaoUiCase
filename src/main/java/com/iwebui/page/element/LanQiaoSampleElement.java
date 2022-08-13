package com.iwebui.page.element;

import org.openqa.selenium.By;

public class LanQiaoSampleElement {
    /**
     * admin登录示例--操作页面元素
     */
    public static final By LOGIN = By.id("adminname");
    public static final By PWD = By.id("adminpwd");
    public static final By BUTTON = By.cssSelector("input:nth-child(6)");

    /**
     * 注册--操作页面元素
     */
    public static final By relogin =By.xpath("/html/body/div/div/a[2]");
    public static final By loginname = By.id("loginname");
    public static final By loginpass = By.id("loginpass");
    public static final By reloginpass = By.id("reloginpass");
    public static final By email = By.id("email");
    public static final By verifyCode = By.id("verifyCode");
    public static final By submitBtn = By.id("submitBtn");

    /**
     * 登录--操作页面元素
     */
    public static final By pre = By.cssSelector("a:nth-child(1)");
    public static final By linkTextLogin = By.linkText("登录");
    public static final By name = By.id("loginname");
    public static final By pass = By.id("loginpass");
    public static final By code = By.id("verifyCode");
    public static final By btn = By.id("submitBtn");

}
