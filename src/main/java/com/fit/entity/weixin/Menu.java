package com.fit.entity.weixin;

import org.apache.ibatis.type.Alias;

/**
 * 菜单
 */
@Alias("wx_menu")
public class Menu {

    private ComplexButton[] button;

    public ComplexButton[] getButton() {
        return button;
    }

    public void setButton(ComplexButton[] button) {
        this.button = button;
    }
}
