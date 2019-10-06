package com.qiuhua.kkk.control;

import com.qiuhua.kkk.Mapper.Usermapper;
import com.qiuhua.kkk.Model.Question;
import com.qiuhua.kkk.Model.User;
import com.qiuhua.kkk.dto.paginationDTO;
import com.qiuhua.kkk.dto.questionDTO;
import com.qiuhua.kkk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
//    这里指定根目录
    @Autowired
    private Usermapper usermapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ){

        Cookie[] cookies = request.getCookies();
        if(cookies != null&&cookies.length!=0)
            for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user=usermapper.findbyToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        paginationDTO pagination =questionService.List(page,size);
        model.addAttribute("pagination",pagination );
        return "index";
    }

}
