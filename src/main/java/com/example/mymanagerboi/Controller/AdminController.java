package com.example.mymanagerboi.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    //todo we need the ability to openai ennpoint
}
