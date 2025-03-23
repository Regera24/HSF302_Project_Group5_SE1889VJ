package org.group5.coolcafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("account", new AccountCreationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute @Valid AccountCreationRequest request, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("account", request);
            model.addAttribute("error", "Please fill in required fields!");
            return "register";
        }
        try {
            accountService.createAccount(request);
            redirectAttributes.addFlashAttribute("success", "Register successfully, please sign in!");
            return "redirect:/login";
        } catch (AppException e) {
            model.addAttribute("error", e.getErrorCode().getMessage());
            model.addAttribute("account", request);
            return "register";
        }
    }

    @GetMapping("/forget-password")
    public String forgetPassword(){
        return "forget-password";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String username, RedirectAttributes redirectAttributes){
        try{
            accountService.sendEmail(username);
            redirectAttributes.addFlashAttribute("success", "Email has been sent, please check your inbox!");
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", "User not existed!");
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/forget-password";
        }
        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String setNewPassword(@RequestParam String token, @RequestParam String newPassword, RedirectAttributes redirectAttributes){
        try{
            accountService.setNewPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("success", "Change password successfully, please login!");
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", "Fail to change password, please try again!");
            return "redirect:/forget-password";
        }
        return "redirect:/login";
    }
}
