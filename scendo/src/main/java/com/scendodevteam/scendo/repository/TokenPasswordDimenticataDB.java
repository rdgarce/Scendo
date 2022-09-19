package com.scendodevteam.scendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scendodevteam.scendo.entity.TokenPasswordDimenticata;

@Repository
public interface TokenPasswordDimenticataDB extends JpaRepository<TokenPasswordDimenticata, Long> {
    
    TokenPasswordDimenticata findByToken(String token);
}
