package com.qiuhua.kkk.dto;

public class AccestokenDTO {
    private String client_id;
    private String redirect_uri;
    private String scode;
    private String state;


    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String code) {
        this.scode = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
