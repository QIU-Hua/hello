package com.qiuhua.kkk.dto;

import com.qiuhua.kkk.model.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreat;
    private Integer status;
    private User notifier;
    private String notifierName;
    private Long outerid;
    private String outerTitle;
    private String typeName;
    private Integer type;

}
