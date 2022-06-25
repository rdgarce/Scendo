package com.scendodevteam.scendo.repository;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteUscitaDB extends JpaRepository<UtenteUscita, Long> {

    List<UtenteUscita> findByUtenteAndUscita(Utente utente, Uscita uscita);

}
