package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.EmployeePerformanceDTO;
import org.group5.coolcafe.dto.SoldProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    public List<Long> getNumberOfOrder(Integer year);
    public List<Double> getRevenueOfOrder(Integer year);
    public List<EmployeePerformanceDTO> getEmployeePerformance();
    public List<SoldProductDTO> getProductRatio();
    public Double getTotalAmount();
}
