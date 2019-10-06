package com.qiuhua.kkk.dto;

import lombok.Data;

@Data
public class AccestokenDTO {
    private String client_id;
    private  String redirect_uri;
    private String code;
    private String client_secret;
    private  String state;




    @Override
    public String toString() {
        return "AccestokenDTO{" +
                "client_id='" + client_id + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                ", scode='" + code + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
