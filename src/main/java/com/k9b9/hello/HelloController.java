package com.k9b9.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HelloController
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String home(Model model) {
        return "hello";
    }
}