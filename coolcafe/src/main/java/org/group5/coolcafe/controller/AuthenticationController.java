package org.group5.coolcafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.repository.ProductRepository;
import org.group5.coolcafe.service.AccountService;
import org.group5.coolcafe.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;
    private final ProductService productService;

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

    @GetMapping("/unauthorized")
    public String unauthorizedPage() {
        return "unauthorized";
    }

    @GetMapping("/menu")
    public String productMenu(Model model) {
        List<ProductDTO> products = productService.findAll();

        List<ProductDTO> starter_4 = new ArrayList<>();
        List<ProductDTO> desserts_4 = new ArrayList<>();
        List<ProductDTO> maindish_4 = new ArrayList<>();
        List<ProductDTO> drinks_4 = new ArrayList<>();
        int totalSize = products.size();
        int index = 0;

        if (index < totalSize) {
            starter_4 = products.subList(index, Math.min(index + 4, totalSize));
            index += 4;
        }
        if (index < totalSize) {
            desserts_4 = products.subList(index, Math.min(index + 4, totalSize));
            index += 4;
        }

        if (index < totalSize) {
            maindish_4 = products.subList(index, Math.min(index + 4, totalSize));
            index += 4;
        }
        if (index < totalSize) {
            drinks_4 = products.subList(index, Math.min(index + 4, totalSize));
            index += 4;
        }


        model.addAttribute("list1", starter_4);
        model.addAttribute("list2", desserts_4);
        model.addAttribute("list3", drinks_4);
        model.addAttribute("list4", maindish_4);

        return "menu";
    }
}
