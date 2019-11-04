package com.qiuhua.kkk.service;

import com.qiuhua.kkk.dto.NotificationDTO;
import com.qiuhua.kkk.dto.paginationDTO;
import com.qiuhua.kkk.enums.NotificationStatusEnum;
import com.qiuhua.kkk.enums.NotificationTypeNnum;
import com.qiuhua.kkk.exception.CustomizeErrorCode;
import com.qiuhua.kkk.exception.CustomizeException;
import com.qiuhua.kkk.mapper.NotificationMapper;
import com.qiuhua.kkk.mapper.Usermapper;
import com.qiuhua.kkk.model.Notification;
import com.qiuhua.kkk.model.NotificationExample;
import com.qiuhua.kkk.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public paginationDTO List(Long userId, Integer page, Integer size) {

        paginationDTO<NotificationDTO> paginationDTO = new paginationDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);


        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);


        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<NotificationDTO> notificationArrayList = new ArrayList<>();


        paginationDTO.setData(notificationArrayList);

        if (notifications.size() == 0) {
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();


        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeNnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOS);

        return paginationDTO;

    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeNnum.nameOfType(notification.getType()));

        return notificationDTO;

    }
}


