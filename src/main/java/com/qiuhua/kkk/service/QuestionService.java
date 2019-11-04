package com.qiuhua.kkk.service;

import com.qiuhua.kkk.dto.QuestionQueryDTO;
import com.qiuhua.kkk.dto.paginationDTO;
import com.qiuhua.kkk.dto.questionDTO;
import com.qiuhua.kkk.exception.CustomizeErrorCode;
import com.qiuhua.kkk.exception.CustomizeException;
import com.qiuhua.kkk.mapper.QuestionExtMapper;
import com.qiuhua.kkk.mapper.QuestionMapper;
import com.qiuhua.kkk.mapper.Usermapper;
import com.qiuhua.kkk.model.Question;
import com.qiuhua.kkk.model.QuestionExample;
import com.qiuhua.kkk.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private Usermapper usermapper;

    public paginationDTO List(String search,Integer page, Integer size) {
        if( StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search," ");
            search= Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        paginationDTO<questionDTO> paginationDTO = new paginationDTO();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount=questionExtMapper.countBySearch(questionQueryDTO);



        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){

            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset=size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_creat desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<questionDTO> questionDTOList=new ArrayList<>();

        for(Question question:questions){
            User user=usermapper.selectByPrimaryKey(question.getCreatId());
            questionDTO questionDTO=new questionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public paginationDTO List(Long userId, Integer page, Integer size) {
        paginationDTO<questionDTO> paginationDTO = new paginationDTO();

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatIdEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);



        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        if(page < 1){
            page = 1;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset=size * (page - 1);

       ;
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatIdEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset,size));
        List<questionDTO> questionDTOList=new ArrayList<>();

        for(Question question:questions){
            User user=usermapper.selectByPrimaryKey(question.getCreatId());
            questionDTO questionDTO=new questionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    public questionDTO getbyId(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        questionDTO questionDTO=new questionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=usermapper.selectByPrimaryKey(question.getCreatId());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void creatOrUpdate(Question question) {
        if(question.getId() == null){
//            创建
            question.setGmtCreat(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreat());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);

        }else{
//            更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andCreatIdEqualTo(question.getId());

            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }



    public void incView(Long id) {

           Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }

    public List<questionDTO> selectRelated(questionDTO queryDTO) {
        if( StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexptag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexptag);

        List<Question> questions= questionExtMapper.selectRelated(question);
        List<questionDTO> questionDTOS= questions.stream().map(q->{
            questionDTO questionDTO = new questionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;

    }
}
