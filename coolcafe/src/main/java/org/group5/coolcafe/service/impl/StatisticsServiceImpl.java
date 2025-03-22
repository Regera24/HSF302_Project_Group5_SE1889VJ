package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.EmployeePerformanceDTO;
import org.group5.coolcafe.dto.SoldProductDTO;
import org.group5.coolcafe.repository.OrderRepository;
import org.group5.coolcafe.repository.ProductRepository;
import org.group5.coolcafe.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Long> getNumberOfOrder(Integer year) {
        return orderRepository.findOrdersByMonth(year);
    }

    @Override
    public List<Double> getRevenueOfOrder(Integer year) {
        return orderRepository.findRevenueByMonth(year);
    }

    @Override
    public List<EmployeePerformanceDTO> getEmployeePerformance() {
        List<Object[]> raw = orderRepository.getEmployeePerformance();
        return raw.stream().map(obj ->
                new EmployeePerformanceDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        ((Number) obj[2]).longValue()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<SoldProductDTO> getProductRatio() {
        List<Object[]> raw = productRepository.getProductRatio();
        return raw.stream().map(obj ->
                new SoldProductDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        ((Number) obj[2]).longValue()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public Double getTotalAmount() {
        return orderRepository.getTotalAmount();
    }
}
