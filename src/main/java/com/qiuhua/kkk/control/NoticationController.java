package com.qiuhua.kkk.control;

import com.qiuhua.kkk.dto.NotificationDTO;
import com.qiuhua.kkk.enums.NotificationTypeNnum;
import com.qiuhua.kkk.model.User;
import com.qiuhua.kkk.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NoticationController {
    @Autowired
    private NotificationService notificationServer;


    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationServer.read(id, user);
        if (NotificationTypeNnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeNnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }


    }
}
