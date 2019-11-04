package com.qiuhua.kkk.control;

import com.qiuhua.kkk.csche.TagCache;
import com.qiuhua.kkk.dto.questionDTO;
import com.qiuhua.kkk.model.Question;
import com.qiuhua.kkk.model.User;
import com.qiuhua.kkk.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishControl {
    @Autowired
    private QuestionService questionService;

    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name="id") Long id,
                       Model model){
        questionDTO question=questionService.getbyId(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }
    @GetMapping("/publish")
    public String publish( Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());



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

        String invaled= TagCache.filterInvalid(tag);
        if(StringUtils.isNoneBlank(invaled)){
            model.addAttribute("tag","输入非法标签"+invaled);
            return "publish";
        }

       User user= (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatId(user.getId());
        question.setId(id);
        questionService.creatOrUpdate(question);

            return "redirect:/";

    }
}
