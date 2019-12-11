package com.qiuhua.kkk.control;


import com.qiuhua.kkk.Provide.githubProvide;
import com.qiuhua.kkk.dto.AccestokenDTO;
import com.qiuhua.kkk.dto.Githubuser;
import com.qiuhua.kkk.model.User;
import com.qiuhua.kkk.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,

                           HttpServletResponse response
    ) {
        AccestokenDTO accestokenDTO = new AccestokenDTO();
        accestokenDTO.setClient_id(clientID);
        accestokenDTO.setClient_secret(ClientSecret);
        accestokenDTO.setCode(code);
        accestokenDTO.setRedirect_uri(RedirectUrl);
        accestokenDTO.setState(state);
        String accessToken = githubProvide.getAccessToken(accestokenDTO);
        Githubuser githubuser = githubProvide.githubuser(accessToken);


        if (githubuser != null && (Long) (githubuser.getId()) != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setAvatarUrl(githubuser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));

            //登录成功，写session和cooking
            return "redirect:/";
        } else {
            log.error("callback github error,{}",githubuser);
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
        }
}


