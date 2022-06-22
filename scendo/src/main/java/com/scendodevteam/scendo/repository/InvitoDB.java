package com.scendodevteam.scendo.repository;

import com.scendodevteam.scendo.entity.Invito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitoDB extends JpaRepository<Invito, Long> {
}
