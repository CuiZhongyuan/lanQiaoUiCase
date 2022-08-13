package com.iwebui.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class InventoryEntity implements Serializable {

    @Excel(name = "账户",orderNum = "1",width = 20)
    private String loginname;
    @Excel(name = "密码",orderNum = "2",width = 40)
    private String loginpass;
    @Excel(name = "确认密码",orderNum = "3",width = 40)
    private String reloginpass;
    @Excel(name = "验证码",orderNum = "4",width = 20)
    private String email;
    @Excel(name = "验证码",orderNum = "5",width = 20)
    private String verifyCode;
    private String token;

}
