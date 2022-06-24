package com.scendodevteam.scendo.repository;

import com.scendodevteam.scendo.entity.UtenteUscita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteUscitaDB extends JpaRepository<UtenteUscita, Long> {
}
