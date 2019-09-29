package com.qiuhua.kkk.control;

import com.qiuhua.kkk.Mapper.Usermapper;
import com.qiuhua.kkk.Model.User;
import com.qiuhua.kkk.Provide.githubProvide;
import com.qiuhua.kkk.dto.AccestokenDTO;
import com.qiuhua.kkk.dto.Githubuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeControl {

    @Autowired
    private githubProvide githubProvide;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.redirect.uri}")
    private String RedirectUrl;
    @Value("${github.client.secret}")
    private String ClientSecret;

    @Autowired
    private Usermapper usermapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response
    )      {
        AccestokenDTO accestokenDTO = new AccestokenDTO();
        accestokenDTO.setClient_id(clientID);
        accestokenDTO.setClient_secret(ClientSecret);
        accestokenDTO.setCode(code);
        accestokenDTO.setRedirect_uri(RedirectUrl);
        accestokenDTO.setState(state);
        String accessToken = githubProvide.getAccessToken(accestokenDTO);
        Githubuser githubuser=githubProvide.githubuser(accessToken);



        if(githubuser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubuser.getName());
            System.out.println(githubuser.getName());
            user.setAccountID(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            usermapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登录成功，写session和cooking

            request.getSession().setAttribute("githubuser",githubuser);
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
