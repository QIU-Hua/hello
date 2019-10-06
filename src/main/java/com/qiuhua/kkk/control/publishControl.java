package com.qiuhua.kkk.control;

import com.qiuhua.kkk.Mapper.QuestionMapper;
import com.qiuhua.kkk.Mapper.Usermapper;
import com.qiuhua.kkk.Model.Question;
import com.qiuhua.kkk.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishControl {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Usermapper usermapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null||title == ""){
            model.addAttribute("title","标题不能为空");
            return "publish";
        }
        if(description == null||description == ""){
            model.addAttribute("description","问题不能为空");
            return "publish";
        }
        if(tag == null||tag == ""){
            model.addAttribute("tag","标签不能为空");
            return "publish";
        }

        User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null&&cookies.length!=0)
            for (Cookie cookie : cookies) {
             if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user=usermapper.findbyToken(token);
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }

                break;
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatId(user.getId());
        question.setGmtCreat(System.currentTimeMillis());
        question.setGmtModified(question.getGmtModified());
        questionMapper.create(question);
            return "redirect:/";

    }
}
