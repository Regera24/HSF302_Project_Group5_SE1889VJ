package org.group5.coolcafe.controller;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.EmployeePerformanceDTO;
import org.group5.coolcafe.dto.SoldProductDTO;
import org.group5.coolcafe.service.AccountService;
import org.group5.coolcafe.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final AccountService accountService;

    @GetMapping("/dashboard")
    public String index(Model model, @RequestParam(required = false, defaultValue = "2025") int year){
        model.addAttribute("numberOfOrders", statisticsService.getEmployeePerformance().stream().map(EmployeePerformanceDTO::getNumberOfOrders).reduce(0L, Long::sum));
        model.addAttribute("revenueOfOrders", statisticsService.getTotalAmount());
        model.addAttribute("totalEmployee", accountService.getNumberOfEmployee());
        model.addAttribute("totalCustomer", accountService.getNumberOfCustomer());

        return "/dashboard/index";
    }

    @GetMapping("/statistics/selling")
    public String chart(Model model, @RequestParam(required = false, defaultValue = "2025") int year){
        List<String> listStaff = statisticsService.getEmployeePerformance()
                .stream()
                .map(item -> String.format("\"%s\"", item.getName()))
                .toList();

        Long sum = statisticsService.getEmployeePerformance().stream().map(EmployeePerformanceDTO::getNumberOfOrders).reduce(0L, Long::sum);
        List<Double> ratio = statisticsService.getEmployeePerformance().stream().map(item -> (double)(item.getNumberOfOrders()*100/(double)sum)).toList();

        List<String> listProduct = statisticsService.getProductRatio()
                .stream()
                .map(item -> String.format("\"%s\"", item.getName()))
                .toList();
        Long soldSum = statisticsService.getProductRatio().stream().map(SoldProductDTO::getQuantity).reduce(0L, Long::sum);
        List<Double> soldRatio = statisticsService.getProductRatio().stream().map(item -> (double)(item.getQuantity()*100)/(double)soldSum).toList();

        model.addAttribute("numberOfOrders", statisticsService.getNumberOfOrder(year));
        model.addAttribute("selectedYear", year);
        model.addAttribute("revenueOfOrders", statisticsService.getRevenueOfOrder(year));
        model.addAttribute("listStaff", listStaff);
        model.addAttribute("performanceRatio",ratio);
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("soldRatio", soldRatio);

        return "/dashboard/charts";
    }
}
