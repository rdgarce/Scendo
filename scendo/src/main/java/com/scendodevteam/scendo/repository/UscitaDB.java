package com.scendodevteam.scendo.repository;


import com.scendodevteam.scendo.entity.Uscita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UscitaDB extends JpaRepository<Uscita,Long> {
}
