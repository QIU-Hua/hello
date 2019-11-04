package com.qiuhua.kkk.mapper;

import com.qiuhua.kkk.dto.QuestionQueryDTO;
import com.qiuhua.kkk.model.Question;
import com.qiuhua.kkk.model.QuestionExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;


public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question record);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
