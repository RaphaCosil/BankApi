package com.example.bankapicode.repository;

import com.example.bankapicode.model.ClienteData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteData, String> {

}
