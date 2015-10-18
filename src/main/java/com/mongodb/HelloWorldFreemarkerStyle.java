package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renatn on 15.10.2015. ;=)
 */
public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) {
        Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");
        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap<>();
            helloMap.put("name", "Renat");
            helloTemplate.process(helloMap, writer);

            System.out.println(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
