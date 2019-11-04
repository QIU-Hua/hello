package com.qiuhua.kkk.csche;

import com.qiuhua.kkk.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTag(Arrays.asList("js","html","css","php","java","python","node","C","C++","node.js","html5","javascript","bush"
         ,"shell","scala","lua","coffeescript","typescript","objective-c"));
        tagDTOS.add(program);

        TagDTO framework =new TagDTO();
        framework.setCategoryName("语言框架");
        framework.setTag(Arrays.asList("laravel","spring","django","flask","yii","express","ruby-on-rails","tornado"));
        tagDTOS.add(framework);

        TagDTO server =new TagDTO();
        server.setCategoryName("服务器");
        server.setTag(Arrays.asList("linux","tomcat","unix","docker","nginx","centos","ubuntu","hadoop"));
        tagDTOS.add( server);

        TagDTO db =new TagDTO();
        db.setCategoryName("数据库");
        db.setTag(Arrays.asList("mysql","redis","sql","oracle","nosql","sqlserver"));
        tagDTOS.add( db);

        TagDTO tool =new TagDTO();
        tool.setCategoryName("工具");
        tool.setTag(Arrays.asList("hub","git-hub","vim","visual-studio-code","submit-text","xcode","intellij-idea","eclipse","svn","ide"));
        tagDTOS.add(tool);
        return tagDTOS;

    }

        public  static String filterInvalid(String tags){
            String[] split = StringUtils.split(tags, ",");
            List<TagDTO> tagDTOS =get();
            List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTag().stream()).collect(Collectors.toList());
            String invalid=Arrays.stream(split).filter(t ->!tagList.contains(t)).collect(Collectors.joining(","));
            return  invalid;

        }
}
