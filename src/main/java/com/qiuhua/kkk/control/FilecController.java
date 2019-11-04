package com.qiuhua.kkk.control;

import com.qiuhua.kkk.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FilecController {
    @RequestMapping("/files/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/loges/editormd-logo-120x120.png");
        return fileDTO;
    }

}
