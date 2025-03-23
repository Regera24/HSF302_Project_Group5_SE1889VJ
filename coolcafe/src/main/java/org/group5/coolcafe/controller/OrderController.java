package org.group5.coolcafe.controller;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.order.OrderCreationRequest;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderUpdateRequest;
import org.group5.coolcafe.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public void createOrder(@RequestBody OrderCreationRequest request){
        orderService.createOrder(request);
    }

    @PutMapping("/order/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest request){
        orderService.updateOrder(id,request);
    }

    @GetMapping("/orders")
    public String getOrders(Model model){
        List<OrderDTO> orderDTOS = orderService.getOrders(0, 10);
        model.addAttribute("orderList", orderDTOS);
        return "/dashboard_layout/list_order";
    }
}
