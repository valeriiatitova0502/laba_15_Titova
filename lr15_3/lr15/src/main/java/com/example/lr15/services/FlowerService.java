package com.example.lr15.services;

import com.example.lr15.specifications.FlowerSpecifications;
import com.example.lr15.entities.Flower;
import com.example.lr15.repositories.FlowerRepository; // Assuming this is the correct repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerService {
    private final FlowerRepository repository;

    @Autowired
    public FlowerService(FlowerRepository repository) {
        this.repository = repository;
    }

    public Flower getById(Integer id) {
        Flower flower = repository.findById(id).orElse(null);
        if (flower == null) throw new UsernameNotFoundException("");
        return flower;
    }

    public Page<Flower> getAllFlowers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Flower> getAllFlowers(String name, String flower, Integer priceFrom, Integer priceTo, Pageable pageable) {
        Specification<Flower> specification = Specification
                .where(FlowerSpecifications.hasName(name))
                .and(FlowerSpecifications.hasFlower(flower))
                .and(FlowerSpecifications.hasPriceFrom(priceFrom))
                .and((FlowerSpecifications.hasPriceTo(priceTo)));
                //.and(FlowerSpecifications.hasType(quantity));
        return repository.findAll(specification, pageable);
    }

    public List<Flower> getTopFlowers() {
        // Реализуйте логику для получения топ-3 цветов (например, сортировка по просмотрам и выбор первых трех)
        List<Flower> flowers = repository.findAll(); // Используйте ваш инжектированный репозиторий
        flowers.sort(Comparator.comparing(Flower::getViews).reversed()); // Сортировка в обратном порядке
        return flowers.stream().limit(3).collect(Collectors.toList()); // Возвращение первых трех цветов
    }


    public void add(Flower flower) {
        flower.setViews(0);
        repository.save(flower);
    }

    public void delete(Flower flower) {
        repository.delete(flower);
    }

    public void update(Flower existing, Flower updated) {
        if (updated.getName() != null && !updated.getName().isBlank()) {
            existing.setName(updated.getName());
        }
        if (updated.getFlower() != null) {
            existing.setFlower(updated.getFlower());
        }
        if (updated.getPrice() != null) {
            existing.setPrice(updated.getPrice());
        }
        if (updated.getQuantity() != null) {
            existing.setQuantity(updated.getQuantity());
        }
        repository.save(existing);
    }


    public void incViews(Flower flower) {
        flower.setViews(flower.getViews() + 1);
        repository.save(flower);
    }
}
