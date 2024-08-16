package com.example.bankapicode.repository;

import com.example.bankapicode.model.ClienteData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteData, String> {
    List<ClienteData> findAllByNome(String nome);
    List<ClienteData> findAllByEmail(String email);
    List<ClienteData> findAllByTelefone(String telefone);
}