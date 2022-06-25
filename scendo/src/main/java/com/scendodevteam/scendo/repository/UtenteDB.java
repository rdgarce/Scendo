package com.scendodevteam.scendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scendodevteam.scendo.entity.Utente;

@Repository
public interface UtenteDB extends JpaRepository<Utente, Long> {

    Utente findByEmail(String email);

    boolean existsByEmail(long email);

    
}