package com.qiuhua.kkk.advice;

import com.alibaba.fastjson.JSON;
import com.qiuhua.kkk.dto.ResultDTO;
import com.qiuhua.kkk.exception.CustomizeErrorCode;
import com.qiuhua.kkk.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
 public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)

    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDTO resultDTO = null;
        if ("application/json".equals(contentType)) {
            //返回json
            if (e instanceof CustomizeException) {
                resultDTO.errorOf((CustomizeException) e);
            } else {
                //返回错误页面
                resultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try
            {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException e1){ }
            return  null;
        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");

        }
    }
}