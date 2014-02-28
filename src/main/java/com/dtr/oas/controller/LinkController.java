package com.dtr.oas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinkController {

    @RequestMapping(value = "/")
    public String mainPage() {
        return "home";
    }

    @RequestMapping(value = "/index")
    public String indexPage() {
        return "home";
    }

}
