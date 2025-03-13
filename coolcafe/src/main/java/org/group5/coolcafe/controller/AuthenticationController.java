package org.group5.coolcafe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    @GetMapping("/")
    public String home(){
        return "shop";
    }
}
