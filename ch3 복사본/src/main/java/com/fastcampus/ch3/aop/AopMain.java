package com.fastcampus.ch3.aop;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{
       Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass");
       Object o = myClass.newInstance();

       MyAdvice myAdvice = new MyAdvice();

       for(Method m : myClass.getDeclaredMethods()){ // 클래스에 선언된 메서드 하나씩 가져오기
           myAdvice.invoke(m, o, null);

       }
    }
}

class MyAdvice {

    Pattern p = Pattern.compile("a.*"); // a로 시작하는 문자열
    boolean matches(Method m){//지정된 메서드가 패턴에 일치하는지 알려줌
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();

    }
    void invoke(Method m, Object obj, Object... args) throws Exception {
        if(m.getAnnotation(Transactional.class)!=null)
            System.out.println("[before]{");

        m.invoke(obj, args);

        if(m.getAnnotation(Transactional.class)!=null)
            System.out.println("}[after]");
    }
    }



class MyClass{
    @Transactional
    void aaa() {
        System.out.println("aaa() is called.");
    }

    void aaa2(){
        System.out.println("aaa2() is called.");
    }

    void bbb(){
        System.out.println("bbb() is called.");
    }
}
