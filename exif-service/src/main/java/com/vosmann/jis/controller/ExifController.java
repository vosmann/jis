package com.vosmann.jis.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ExifController {

    @RequestMapping("/")
    @ResponseBody
    String test() {
        return "Exif service reporting in.";
    }

    public static void main(final String[] args) {
        SpringApplication.run(ExifController.class, args);
    }

}
