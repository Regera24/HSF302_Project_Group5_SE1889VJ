package org.group5.coolcafe.controller.ProductController;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.ProductDTO.ProductCreationRequest;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.impl.CategoryServiceImpl;
import org.group5.coolcafe.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public String GetAllProducts(@RequestParam(value = "name", defaultValue = "") String name,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 Model model) {
        model.addAttribute("name", name);
        model.addAttribute( "productUpdate", new ProductCreationRequest());
        model.addAttribute("productDTO", new ProductCreationRequest());
        Page<ProductDTO> productDTOPage = productService.findByName(page, size, "id", false, "");
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("totalPages", productDTOPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("productPage", productDTOPage.getContent());
        return "/product_layout/products";
    }

    @GetMapping("/product-manage")
    public String getProductByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "descending", defaultValue = "false") boolean descending,
            Model model) {
        model.addAttribute("name", name);
        model.addAttribute( "productUpdate", new ProductCreationRequest());
        model.addAttribute("productDTO", new ProductCreationRequest());
        Page<ProductDTO> productPage;
        if(categoryId != null){
             productPage=productService.findByNameAndCategory(page,size,sortBy,descending,name,categoryId);
        }else{
             productPage = productService.findByName(page, size, sortBy, descending, name);
        }
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        model.addAttribute("productPage", productPage.getContent());
        return "/product_layout/products";
    }
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductCreationRequest productCreationRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(fieldError -> mapCustomErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                    .findFirst()
                    .orElse("Something went wrong");

            redirectAttributes.addFlashAttribute("error", errorMessage);
            redirectAttributes.addFlashAttribute( "productUpdate", new ProductCreationRequest());
            redirectAttributes.addFlashAttribute("productDTO", new ProductCreationRequest());
            redirectAttributes.addFlashAttribute("categoryList", categoryService.findAll());
            Page<ProductDTO> productDTOPage = productService.findByName(0, 10, "id", false, "");
            redirectAttributes.addFlashAttribute("totalPages", productDTOPage.getTotalPages());
            redirectAttributes.addFlashAttribute("productPage", productDTOPage.getContent());
            return "redirect:/product/list";
        }
        try{
            productService.create(productCreationRequest);
            redirectAttributes.addFlashAttribute("success", "Create product success");
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Upload image failed");
        }
        return "redirect:/product/list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") long id) {
        productService.delete(id);
        return "redirect:/product/list";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute ProductCreationRequest productCreationRequest, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(fieldError -> mapCustomErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                    .findFirst()
                    .orElse("Something went wrong");

            redirectAttributes.addFlashAttribute("error", errorMessage);
            redirectAttributes.addFlashAttribute( "productUpdate", new ProductCreationRequest());
            redirectAttributes.addFlashAttribute("productDTO", new ProductCreationRequest());
            redirectAttributes.addFlashAttribute("categoryList", categoryService.findAll());
            Page<ProductDTO> productDTOPage = productService.findByName(0, 10, "id", false, "");
            redirectAttributes.addFlashAttribute("totalPages", productDTOPage.getTotalPages());
            redirectAttributes.addFlashAttribute("productPage", productDTOPage.getContent());
            return "redirect:/product/list";
        }
        try{
            productService.update(productCreationRequest);
            redirectAttributes.addFlashAttribute("success", "Update product success");
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Upload image failed");
        }
        return "redirect:/product/list";
    }

    private String mapCustomErrorMessage(String field, String defaultMessage) {
        switch (field) {
            case "name":
                return "Product name is required and must meet the criteria.";
            case "price":
                return "Please provide a valid price for the product.";
            case "categoryId":
                return "You must select a category for this product.";
            case "estimatedQuantity":
                return "Quantity must be greater than or equal to 0.";
            case "image":
                return "Please upload a valid image for the product.";
            default:
                return defaultMessage;
        }
    }
}
