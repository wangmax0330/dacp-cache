package com.pikai;

import org.springframework.context.support.GenericXmlApplicationContext;

public class StartUp {
	public static void main(String[] args) throws InterruptedException {  
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();  
        context.setValidating(false);  
        context.load("classpath*:config/applicationContext*.xml");  
        context.refresh();  
        System.out.println("----------------");
     //   UserService userService = context.getBean(UserService.class);  
//        while (true) {  
//            System.out.println(userService.findUser());  
//            Thread.sleep(10000);  
//        }  
    }  
}