package com.example.lr15.repositories;

import com.example.lr15.entities.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface FlowerRepository extends JpaRepository<Flower,Integer>, JpaSpecificationExecutor<Flower> {
}
