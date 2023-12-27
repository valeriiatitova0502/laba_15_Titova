package com.example.lr15.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.lr15.entities.Authority;
import com.example.lr15.entities.User;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    // Метод для поиска объекта Authority по сущности User
    Authority findAuthorityByUser(User user);
}
