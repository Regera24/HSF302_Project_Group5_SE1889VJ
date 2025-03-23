package org.group5.coolcafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.manage_account.ManageAccountDTO;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.impl.AccountServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountService;

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
        model.addAttribute("accountDTO", new ManageAccountDTO());
        return "/dashboard_layout/list_account";
    }

    @PostMapping("/create")
    public String createAccount(
            @ModelAttribute @Valid ManageAccountDTO manageAccountDTO,
            RedirectAttributes redirectAttributes){
        try {
            accountService.createAccount(manageAccountDTO);
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/account/list";
        }
        redirectAttributes.addFlashAttribute("success", "Create account successfully!!!");
        return "redirect:/account/list";
    }

    @PostMapping("/update")
    public String updateAccount(
            @ModelAttribute @Valid ManageAccountDTO manageAccountDTO,
            RedirectAttributes redirectAttributes){
        try {
            accountService.updateAccount(manageAccountDTO.getId(), manageAccountDTO);
        } catch (AppException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/account/list";
        }
        redirectAttributes.addFlashAttribute("success", "Update account successfully!!!");
        return "redirect:/account/list";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam String accountId, RedirectAttributes redirectAttributes) {
        accountService.deleteAccount(Long.parseLong(accountId));
        redirectAttributes.addFlashAttribute("success", "Delete account successfully!!!");
        return "redirect:/account/list";
    }

    @GetMapping("/profile")
    public String profile(){
        return "/dashboard_layout/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute @Valid ManageAccountDTO manageAccountDTO, RedirectAttributes redirectAttributes) throws IOException {
        accountService.updateProfile("sa", manageAccountDTO);
        redirectAttributes.addFlashAttribute("success", "Update profile successfully!!!");
        return "/dashboard_layout/profile";
    }

    @GetMapping("/widgets")
    public String widget(){
        return "/dashboard_layout/widgets";
    }

    @GetMapping("/icons")
    public String widget2(){
        return "/dashboard_layout/font-awesome-icons";
    }

    @GetMapping("/alert")
    public String widget3(){
        return "/dashboard_layout/sweetalert";
    }

    @GetMapping("/forms")
    public String forms(){
        return "/dashboard_layout/forms";
    }

}
