package com.qiuhua.kkk.enums;

public enum NotificationTypeNnum {
    REPLY_QUESTION("回复了问题", 1),
    REPLY_COMMENT("回复了评论", 2);

    private int type;
    private String name;


    NotificationTypeNnum(String name, int Type) {
        this.name = name;
        this.type = Type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static String nameOfType(int type){
        for (NotificationTypeNnum notificationTypeNnum : NotificationTypeNnum.values()) {
            if(notificationTypeNnum.getType() == type){
                return notificationTypeNnum.getName();
            }
        }
        return "";
    }
}