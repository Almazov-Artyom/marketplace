package ru.almaz.authservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
public class MainController {
    @GetMapping("/welcome")
    public String hello() {
        log.info("MainController: hello");
        return "welcome";
    }

    @GetMapping("/shops")
    public String shops() {return "Раздел с магазинами, доступен продавцам";}

    @GetMapping("/products")
    public String products() {return "Раздел с товарами, доступен покупателям";}

}
