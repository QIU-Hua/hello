package com.qiuhua.kkk.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
//    这里指定根目录
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
