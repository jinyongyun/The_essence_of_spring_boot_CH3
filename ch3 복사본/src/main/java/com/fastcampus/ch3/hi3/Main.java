package com.fastcampus.ch3.hi3;

import com.fastcampus.ch3.hi3.AppConfig;
import com.google.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
@Component
class Car {
   // @Resource(name="superEngine")
//    @Autowired
//    @Qualifier("superEngine")
    @Inject
   Engine engine;
   // @Autowired
    @Resource(name="door")
    Door door;

//    @Autowired
//    public Car(Engine[] engine, Door door) {
//        this.engine = engine;
//        this.door = door;
//    }

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

class Engine{}

@Component
class SuperEngine extends Engine {}

@Component
class TurboEngine extends Engine {}

@Component
class Door{}

public class Main {
    public static void main(String[] args) {
        //AC 생성 - AC의 설정파일은 AppConfig.class 자바 설정
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Car car = (Car)ac.getBean("car");
        System.out.println("car = " + car);
    }
}
