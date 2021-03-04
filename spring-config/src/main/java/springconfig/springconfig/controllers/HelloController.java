package springconfig.springconfig.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Value("${text.value}")
    private String value;

    @Value("${text.default:default value}")
    private String defaultValue;

    @Value("${text.list}")
    private List<String> list;


    @GetMapping("/hello")
    public String helloClass(){
        return value + defaultValue + list ;
    }
}
