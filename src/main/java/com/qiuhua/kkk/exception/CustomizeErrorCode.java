package com.qiuhua.kkk.exception;



public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"已经迷路"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何评论"),
    NO_LOGIN(2003,"当前操做未登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器冒烟了，请重新试试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUNT(2006,"回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入的内容为空"),
    READ_NOTIFICATION_FAIL(2008,"另一个数据"),
    NOTIFICATION_NOT_FOUND(2009,"没有消息")
    ;




    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
