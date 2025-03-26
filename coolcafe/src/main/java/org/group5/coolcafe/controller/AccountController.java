package org.group5.coolcafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.AccountDTO;
import org.group5.coolcafe.dto.ProfileDTO;
import org.group5.coolcafe.dto.manage_account.ManageAccountDTO;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.impl.AccountServiceImpl;
import org.group5.coolcafe.utils.CloudinaryService;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountService;
    private final RestClient.Builder builder;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/list")
    public String getAccountList(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        Page<ManageAccountDTO> accountDTOPage = accountService.getAllAccounts(keyword,page, size, sortField, sortDirection);
        model.addAttribute("accounts", accountDTOPage.stream().toList());
        model.addAttribute("currentPage", page+1);
        model.addAttribute("totalPages", accountDTOPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("account", new ManageAccountDTO());
        return "/dashboard_layout/list_account";
    }

    @PostMapping("/create")
    public String createAccount(
            @ModelAttribute("account") @Valid ManageAccountDTO manageAccountDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model){
        if (result.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {errors.put(error.getField(), error.getDefaultMessage());});
            model.addAttribute("errors", errors);
            model.addAttribute("showCreateModal", true);
            return "/dashboard_layout/list_account";
        }
        try {
            accountService.createAccount(manageAccountDTO);
            redirectAttributes.addFlashAttribute("success", "Create account successfully!!!");
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/account/list";
        }
        return "redirect:/account/list";
    }

    @PostMapping("/update")
    public String updateAccount(
            @ModelAttribute @Valid ManageAccountDTO manageAccountDTO,
            RedirectAttributes redirectAttributes){
        try {
            accountService.updateAccount(manageAccountDTO.getId(), manageAccountDTO);
            redirectAttributes.addFlashAttribute("success", "Update account successfully!!!");
        } catch (AppException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/account/list";
        }
        return "redirect:/account/list";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam String accountId, RedirectAttributes redirectAttributes) {
        accountService.deleteAccount(Long.parseLong(accountId));
        redirectAttributes.addFlashAttribute("success", "Delete account successfully!!!");
        return "redirect:/account/list";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ManageAccountDTO account = accountService.getAccountByUsername(username);
        ProfileDTO profileDTO = ProfileDTO.builder()
                                            .name(account.getName())
                                            .dob(account.getDob())
                                            .email(account.getEmail())
                                            .phoneNumber(account.getPhoneNumber())
                                            .avatar(account.getAvatar())
                                            .build();
        model.addAttribute("profile", profileDTO);
        return "/dashboard_layout/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @ModelAttribute("profile") @Valid ProfileDTO profileDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {errors.put(error.getField(), error.getDefaultMessage());});
            model.addAttribute("errors", errors);
            return "/dashboard_layout/profile";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            accountService.updateProfile(username, profileDTO);
            redirectAttributes.addFlashAttribute("success", "Update profile successfully!!!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "/dashboard_layout/profile";
    }
}
