package com.umeng.soexample.model;

public class HomeIconInfo {
    private String iconName;
    private String iconPic;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }

    public HomeIconInfo(String iconName, String iconPic) {
        this.iconName = iconName;
        this.iconPic = iconPic;
    }
}
