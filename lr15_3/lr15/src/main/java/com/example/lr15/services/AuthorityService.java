package com.example.lr15.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.lr15.entities.Authority;
import com.example.lr15.entities.User;
import com.example.lr15.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    // Сервис, предоставляющий методы для работы с сущностью Authority
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    // Получение объекта Authority по сущности User
    public Authority getAuthorityByUser(User user) {
        return authorityRepository.findAuthorityByUser(user);
    }


    // Сохранение объекта Authority
    public void save(Authority authority) {
        authorityRepository.save(authority);
    }
}
