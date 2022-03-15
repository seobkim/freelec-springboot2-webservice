package com.jojoldu.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 어노테이션이 생설될 수있는 위치를 지정
// PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
// 이 외에도 클래스 선언문에 쓸 수있는 TYPE 등이 있음
@Target(ElementType.PARAMETER)

//애노테이션의 라이프 사이클 즉, 애노테이션이 언제까지 살아 남아 있을지를 정하는 것
//출처: https://jeong-pro.tistory.com/234 [기본기를 쌓는 정아마추어 코딩블로그]
@Retention(RetentionPolicy.RUNTIME)

// 이 파일을 어노테이션 클래스로 지정
// LoginUser라는 어노테이션이 생성되었다고 보면됨
public @interface LoginUser {
}
