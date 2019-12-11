package com.qiuhua.kkk.control;

import com.qiuhua.kkk.dto.CommentCreateDTO;
import com.qiuhua.kkk.dto.CommentDTO;
import com.qiuhua.kkk.dto.ResultDTO;
import com.qiuhua.kkk.enums.CommentTypeEnum;
import com.qiuhua.kkk.exception.CustomizeErrorCode;
import com.qiuhua.kkk.mapper.CommentMapper;
import com.qiuhua.kkk.model.Comment;
import com.qiuhua.kkk.model.User;
import com.qiuhua.kkk.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",  method= RequestMethod.POST)
    public Object post(
            @RequestBody CommentCreateDTO commentCreatDTO,
            HttpServletRequest request
    ) {
        User user=(User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreatDTO == null || StringUtils.isBlank(commentCreatDTO.getContent() )){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreatDTO.getParentId());
        comment.setContent(commentCreatDTO.getContent());
        comment.setType(commentCreatDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf();

    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",  method= RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(value = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listbytargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
        }

}
