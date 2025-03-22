package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = """
            WITH Months AS (
                SELECT '01' AS month UNION ALL
                SELECT '02' UNION ALL
                SELECT '03' UNION ALL
                SELECT '04' UNION ALL
                SELECT '05' UNION ALL
                SELECT '06' UNION ALL
                SELECT '07' UNION ALL
                SELECT '08' UNION ALL
                SELECT '09' UNION ALL
                SELECT '10' UNION ALL
                SELECT '11' UNION ALL
                SELECT '12'
            )
            SELECT 
                COUNT(o.id) AS numberOfOrders
            FROM Months m
            LEFT JOIN orders o 
                ON FORMAT(o.created_at, 'MM') = m.month
                AND (:year IS NULL OR YEAR(o.created_at) = :year)
            GROUP BY m.month
            ORDER BY m.month
        """, nativeQuery = true)
    List<Long> findOrdersByMonth(@Param("year") Integer year);

    @Query(value = """
            WITH Months AS (
                SELECT '01' AS month UNION ALL
                SELECT '02' UNION ALL
                SELECT '03' UNION ALL
                SELECT '04' UNION ALL
                SELECT '05' UNION ALL
                SELECT '06' UNION ALL
                SELECT '07' UNION ALL
                SELECT '08' UNION ALL
                SELECT '09' UNION ALL
                SELECT '10' UNION ALL
                SELECT '11' UNION ALL
                SELECT '12'
            )
            SELECT 
                SUM(o.total_amount) AS numberOfOrders
            FROM Months m
            LEFT JOIN orders o 
                ON FORMAT(o.created_at, 'MM') = m.month
                AND (:year IS NULL OR YEAR(o.created_at) = :year)
            GROUP BY m.month
            ORDER BY m.month
        """, nativeQuery = true)
    List<Double> findRevenueByMonth(@Param("year") Integer year);

    @Query(value = """
            select a.id, a.name, count(*) as numberOfOrder
            from orders o
            join account a
            on o.accountid = a.id
            group by a.name, a.id;
        """, nativeQuery = true)
    List<Object[]> getEmployeePerformance();

    @Query(value = """
            select sum(o.total_amount) from orders o;
        """, nativeQuery = true)
    Double getTotalAmount();
}
