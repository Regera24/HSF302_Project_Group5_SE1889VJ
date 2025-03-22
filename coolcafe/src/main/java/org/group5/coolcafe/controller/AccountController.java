package org.group5.coolcafe.controller;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.AccountDTO;
import org.group5.coolcafe.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountService;

    @GetMapping("/list")
    public String getAccountList(Model model){
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "/dashboard_layout/tables/datatables";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute AccountDTO accountDTO){
        accountService.createAccount(accountDTO);
        return "redirect:/account/list";
    }

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") int id){

        return "list_account";
    }
}
