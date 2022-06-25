package com.scendodevteam.scendo.repository;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitoDB extends JpaRepository<Invito, Long> {

    List<Invito> findByUscitaAndUtenteInvitato(Uscita uscita, Utente utenteInvitato);
}
