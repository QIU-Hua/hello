package com.qiuhua.kkk.Provide;

import com.alibaba.fastjson.JSON;
import com.qiuhua.kkk.dto.AccestokenDTO;
import com.qiuhua.kkk.dto.Githubuser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class githubProvide {


    public String getAccessToken(AccestokenDTO accestokenDTO) {
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accestokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
         System.out.println(string);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Githubuser githubuser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()
        ) {
            String string=response.body().string();
            Githubuser githubuser = JSON.parseObject(string, Githubuser.class);
            return githubuser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}