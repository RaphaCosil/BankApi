package com.example.bankapicode.repository;

import com.example.bankapicode.model.ContaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<ContaData, String> {
    List<ContaData> findAllBySaldoNull();
    List<ContaData> findAllOrderBySaldo(Double saldo);
    List<ContaData> findAllBySaldoIsLessThan(Double saldo);
}