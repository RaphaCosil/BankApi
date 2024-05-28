package com.example.bankapicode.repository;

import com.example.bankapicode.model.ContaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaData, String> {
}
