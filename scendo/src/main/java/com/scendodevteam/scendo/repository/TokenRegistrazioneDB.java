package com.scendodevteam.scendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scendodevteam.scendo.entity.TokenRegistrazione;

@Repository
public interface TokenRegistrazioneDB extends JpaRepository<TokenRegistrazione, Long> {

    TokenRegistrazione findByToken(String token);
}
