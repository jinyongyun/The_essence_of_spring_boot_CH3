package com.fastcampus.ch3.di2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
class Car {
    @Autowired Engine engine;
    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoor(Door door) {
        this.door = door;
    }
}

class SportsCar extends Car{
    @Override
    public String toString() {
        return "SportsCar{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

class Engine {}

class Door {}
public class Main {

    public static void main(String[] args) {
        //생성자를 AppContext(Class clazz)를 사용
        AppContext ac = new AppContext(AppConfig.class);
        Car car = (Car)ac.getBean("car"); //byName
        Car car2 = (Car)ac.getBean(Car.class); //byType
        Engine engine = (Engine) ac.getBean("engine"); //byName
        Door door = (Door) ac.getBean(Door.class); //byType

        //bean들끼리의 관계 설정 - 수동
//            car.setEngine(engine);
//            car.setDoor(door);

        System.out.println("car2 = " + car2);
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);

    }
}
