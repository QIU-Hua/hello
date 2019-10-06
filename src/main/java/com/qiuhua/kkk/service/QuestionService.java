package com.qiuhua.kkk.service;

import com.qiuhua.kkk.Mapper.QuestionMapper;
import com.qiuhua.kkk.Mapper.Usermapper;
import com.qiuhua.kkk.Model.Question;
import com.qiuhua.kkk.Model.User;
import com.qiuhua.kkk.dto.paginationDTO;
import com.qiuhua.kkk.dto.questionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Usermapper usermapper;

    public paginationDTO List(Integer page, Integer size) {

        paginationDTO paginationDTO = new paginationDTO();
        Integer totalCount=questionMapper.count();

        paginationDTO.setPagination(totalCount,page,size);


        if(page < 1){
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset=size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);
        List<questionDTO> questionDTOList=new ArrayList<>();

        for(Question question:questions){
            User user=usermapper.findbyid(question.getCreatId());
            questionDTO questionDTO=new questionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
}
