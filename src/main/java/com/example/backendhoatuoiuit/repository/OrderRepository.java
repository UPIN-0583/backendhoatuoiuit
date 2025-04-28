package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE DATE(o.orderDate) = :orderDate AND o.status <> 'CANCELLED'")
    BigDecimal getTotalRevenueByDate(@Param("orderDate") LocalDate orderDate);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE FUNCTION('MONTH', o.orderDate) = :month AND FUNCTION('YEAR', o.orderDate) = :year AND o.status <> 'CANCELLED'")
    BigDecimal getTotalRevenueByMonth(@Param("month") int month, @Param("year") int year);
}
