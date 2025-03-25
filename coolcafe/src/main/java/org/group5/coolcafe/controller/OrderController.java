package org.group5.coolcafe.controller;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.OrderDetailConverter;
import org.group5.coolcafe.converter.ProductConverter;
import org.group5.coolcafe.converter.ServeTableConverter;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.group5.coolcafe.dto.order.*;
import org.group5.coolcafe.entity.Order;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.OrderDetailRepository;
import org.group5.coolcafe.repository.OrderRepository;
import org.group5.coolcafe.repository.ProductRepository;
import org.group5.coolcafe.repository.ServeTableRepository;
import org.group5.coolcafe.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ServeTableConverter serveTableConverter;
    private final ServeTableRepository serveTableRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailConverter orderDetailConverter;

    @GetMapping("")
    public String getProducts(Model model) {
        List<ProductDTO> products = productRepository.findAll().stream().map(productConverter::convertProductToProductDTO).collect(Collectors.toList());
        model.addAttribute("products", products);
        List<OrderServeTableDTO> serveTableDTOS = serveTableRepository.findAll().stream().map(serveTableConverter::toServeTableDTO).collect(Collectors.toList());
        model.addAttribute("serveTables", serveTableDTOS);
        return "/order_layout/create-order";
    }

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderCreationRequest request){
        orderService.createOrder(request);
        return "redirect:/order";
    }

    @GetMapping("/update/{id}")
    public String getProductAndServeTable(Model model, @PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        model.addAttribute("orderFound", order);
        List<ProductDTO> products = productRepository.findAll().stream().map(productConverter::convertProductToProductDTO).collect(Collectors.toList());
        model.addAttribute("products", products);
        List<OrderServeTableDTO> serveTableDTOS = serveTableRepository.findAll().stream().map(serveTableConverter::toServeTableDTO).collect(Collectors.toList());
        model.addAttribute("serveTables", serveTableDTOS);
        return "/order_layout/update-order";
    }

    @PutMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest request){
        orderService.updateOrder(id, request);
        return "redirect:/order/manage";
    }

    @GetMapping("/manage")
    public String getOrders(@RequestParam(defaultValue = "0") int pageNo,
                            @RequestParam(defaultValue = "10") int pageSize,
                            Model model){
        List<OrderDTO> orderDTOS = orderService.getOrders(pageNo, pageSize);
        model.addAttribute("orderList", orderDTOS);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", orderService.getTotalPages(pageSize));
        return "/order_layout/order-list";
    }
}
