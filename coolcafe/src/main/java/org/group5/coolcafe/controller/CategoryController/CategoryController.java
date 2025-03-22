package org.group5.coolcafe.controller.CategoryController;


import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;
import org.group5.coolcafe.service.impl.CategoryServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public String getAllCategory(@RequestParam( value = "name", defaultValue = "") String name,Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        model.addAttribute("categoryUpdate", new CategoryDTO());
        model.addAttribute("name", name);
        model.addAttribute("categoryList", categoryService.findAll());
        return "/category_layout/categories";
    }
    @GetMapping("/search")
    public String  getCategoryById(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "dateFrom", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,
            @RequestParam(value = "dateTo", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,
            Model model) {

        List<CategoryDTO> a=categoryService.findByName(name,dateFrom,dateTo);
        model.addAttribute("categoryDTO", new CategoryDTO());
        model.addAttribute("categoryUpdate", new CategoryDTO());
        model.addAttribute("categoryList", a);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("name", name);
        return "/category_layout/categories";
    }

    @PostMapping("/create")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO) {
       categoryService.createCategory(categoryDTO);
        return "redirect:/category/list";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return "redirect:/category/list";
    }
}
