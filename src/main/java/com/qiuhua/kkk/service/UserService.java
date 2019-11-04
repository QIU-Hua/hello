package com.qiuhua.kkk.service;

import com.qiuhua.kkk.mapper.Usermapper;
import com.qiuhua.kkk.model.User;
import com.qiuhua.kkk.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private Usermapper usermapper;

    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = usermapper.selectByExample(userExample);
        if(users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usermapper.insert(user);
        }else{
//            更新数据U
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setName(user.getName());
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());

            usermapper.updateByExampleSelective(updateUser, example);
        }
    }
}
