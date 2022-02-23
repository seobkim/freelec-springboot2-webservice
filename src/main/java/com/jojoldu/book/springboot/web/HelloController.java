package com.jojoldu.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줌
// @RepsonseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌
@RestController
public class HelloController {

    // http 메소드 Get의 요청을 받을 수 있는 api
    // @RequestMapping(method = RequestMethod.get)으로 사용했었음.
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

}
