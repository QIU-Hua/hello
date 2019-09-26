package com.qiuhua.kkk.control;

import com.qiuhua.kkk.Provide.githubProvide;
import com.qiuhua.kkk.dto.AccestokenDTO;
import com.qiuhua.kkk.dto.Githubuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeControl {

    @Autowired
    private githubProvide githubProvide;

    @Value("${github.Client_id}")
    private String clientID;
    @Value("${git.Redirect.uri}")
    private String RedirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccestokenDTO accestokenDTO = new AccestokenDTO();
        accestokenDTO.setClient_id(clientID);
        accestokenDTO.setScode(code);
        accestokenDTO.setRedirect_uri(RedirectUrl);
        accestokenDTO.setState(state);
        String accessToken = githubProvide.getAccessToken(accestokenDTO);
        Githubuser user=githubProvide.githubuser(accessToken);

        return "index";
    }
}
