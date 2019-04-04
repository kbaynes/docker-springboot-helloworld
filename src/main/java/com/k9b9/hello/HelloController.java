package com.k9b9.hello;

import java.net.InetAddress;
import java.util.Date;

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
        String hostname = System.getenv("HOSTNAME");
        if (hostname==null) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("hostname", hostname==null ? "" : hostname);
        model.addAttribute("timestamp", (new Date()).toString());
        return "hello";
    }
}