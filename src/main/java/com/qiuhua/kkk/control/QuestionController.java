package com.qiuhua.kkk.control;

import com.qiuhua.kkk.dto.CommentDTO;
import com.qiuhua.kkk.dto.questionDTO;
import com.qiuhua.kkk.enums.CommentTypeEnum;
import com.qiuhua.kkk.service.CommentService;
import com.qiuhua.kkk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Long id,
                           Model model){


       questionDTO questionDTO= questionService.getbyId(id);
       List<questionDTO> relateQuestions = questionService.selectRelated(questionDTO);

        List<CommentDTO> comments = commentService.listbytargetId(id, CommentTypeEnum.QUESTION);


        //累计评论
       questionService.incView(id);
       model.addAttribute("question",questionDTO);
       model.addAttribute("comments",comments);
       model.addAttribute("relateQuestions",relateQuestions);
        return "question";
    }
}
