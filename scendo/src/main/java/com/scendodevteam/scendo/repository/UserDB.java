package com.scendodevteam.scendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scendodevteam.scendo.model.User;

@Repository
public interface UserDB extends JpaRepository<User, Long> {

    
}