package com.fastcampus.ch3.di4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.util.Arrays;

//@SpringBootApplication //아래의 3개 애너테이션 붙인 것과 같다

class Car {
    public String toString() {
        return "Car{}";
    }
}

class SportsCar extends Car {
    public String toString() {
        return "SportsCar{}";
    }
}

class SportsCar2 extends Car {
    public String toString() {
        return "SportsCar{}";
    }
}


@Component
@Conditional(TrueCondition.class)
class Engine {
    public String toString() {
        return "Engine{}";
    }
}

@Component
@Conditional(OSCondition.class)
class Door {
    public String toString() {
        return "Door{}";
    }
}

class TrueCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }
}

class OSCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        System.out.println("System.getProperties() = " + System.getProperties());
        return env.getProperty("mode").equals("test");
    }
}
@Configuration
@Import({Config1.class, Config2.class})
//@EnableMyAutoConfiguration("test")
class MainConfig { //자바 설정 파일
    @Bean Car car() {return new Car();}

}
class Config1 {
    @Bean Car sportsCar(){return new SportsCar();}
}

class Config2 {
    @Bean Car sportsCar(){return new SportsCar2();}
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportSelector.class)
@interface EnableMyAutoConfiguration {
    String value() default "";
}

class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attr = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableMyAutoConfiguration.class.getName(),false));
        String mode = attr.getString("value");
        if(mode.equals("test"))
           return new String[] {Config1.class.getName()};
        else
            return new String[] {Config2.class.getName()};
    }
}

@EnableConfigurationProperties({MyProperties.class})
@Configuration//@SpringBootConfiguration //@Configuration과 동일
//@EnableAutoConfiguration 디비 설정 안해서 주석처리
@ComponentScan
public class Main implements CommandLineRunner {
    @Autowired
    MyProperties prop; //자동주입
    @Autowired
    ApplicationContext ac;


    @Override
    public void run(String... args) throws Exception { // 인스턴스 메서드
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        Arrays.sort(beanDefinitionNames); // 빈 목록이 담긴 배열을 정렬
        Arrays.stream(beanDefinitionNames) //배열을 스트림으로 변환
                .filter(b->!b.startsWith("org"))
                .forEach(System.out::println); // 스트림의 요소를 하나씩 꺼내서 출력

        System.out.println("prop.getEmail() = " + prop.getEmail());
        System.out.println("prop.getDomain() = " + prop.getDomain());
    }

    public static void main(String[] args) { // static 메서드
        ApplicationContext ac = SpringApplication.run(Main.class, args);
        //ApplicationContext ac = new AnnotationConfigApplicationContext(MainConfig.class, Config1.class, Config2.class); // 자바 설정을 이용하는 AC
       // ApplicationContext ac = new AnnotationConfigApplicationContext(MainConfig.class);

//        MyProperties prop = ac.getBean(MyProperties.class); // 수동으로 빈 검색해서 주입
//        System.out.println("prop.getDomain() = " + prop.getDomain());
//        System.out.println("prop.getEmail() = " + prop.getEmail());
    }

    @Bean
    MyBean myBean() {return new MyBean();}


}

class MyBean {}