package com.example.lr15.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import com.example.lr15.entities.Flower;
import com.example.lr15.services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FlowerController {
    private FlowerService flowerService;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setFlowerServiceService(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("")
    public String showFlowersList(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Flower> flowerPage = flowerService.getAllFlowers(pageable);
        model.addAttribute("flowers", flowerPage.getContent());
        model.addAttribute("flower", new Flower());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flowerPage.getTotalPages());
        List<Flower> topflowers = flowerService.getTopFlowers();
        model.addAttribute("topflowers", topflowers);

        return "flowers";

    }

    @PostMapping("/flowers/addOrUpdate/add")
    public String addFlower(@ModelAttribute(value = "FlowerShop") Flower flower) {
        flowerService.add(flower);
        return "redirect:/";
    }

    @GetMapping("/flowers/addOrUpdate/add")
    public String test(Model model,
                       @RequestHeader(value = "Referer") String referer) {
        Page<Flower> flowerPage = flowerService.getAllFlowers(PageRequest.of(0, 5));
        model.addAttribute("flowers", flowerPage.getContent());
        model.addAttribute("flower", new Flower());
        model.addAttribute("referer", referer);
        return "addOrUpdate";
    }

    @GetMapping("/flowers/addOrUpdate/edit/{id}")
    public String editFlower(Model model, @PathVariable(value = "id") Integer id,
                             @RequestHeader(value = "Referer") String referer) {
        Flower flower = flowerService.getById(id);
        flowerService.incViews(flower);
        model.addAttribute("flower", flower);
        model.addAttribute("referer", referer);
        return "addOrUpdate";
    }

    @PostMapping("/flowers/addOrUpdate/edit")
    public String updateFlower(@ModelAttribute(value = "Flower") Flower updatedFlower) {
        Flower flower = flowerService.getById(updatedFlower.getId());
        flowerService.update(flower, updatedFlower);

        return "redirect:/";
    }

    @GetMapping("/flowers/show/{id}")
    public String showOneFlower(Model model, @PathVariable(value = "id") Integer id,
                                @RequestHeader(value = "Referer") String referer)
    {
        Flower flower = flowerService.getById(id);
        flowerService.incViews(flower);
        model.addAttribute("flower", flower);
        model.addAttribute("referer", referer);
//        System.out.println(model);
        List<Flower> topflowers = flowerService.getTopFlowers();
        model.addAttribute("topflowers", topflowers);

        return "flower-info";
    }

    @GetMapping("/flowers/delete/{id}")
    public String deleteFlower(@PathVariable(value = "id") Integer id) {
        Flower flower = flowerService.getById(id);
        flowerService.delete(flower);
        return "redirect:/";
    }

    @GetMapping("/flowers/filter")
    public String filterFlowers(Model model,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "pricefrom", required = false) Integer pricefrom,
                                @RequestParam(value = "priceto", required = false) Integer priceto,
                                @RequestParam(value = "flowerr", required = false) String flower,
                                @RequestParam(defaultValue = "0") int page) {
        Flower flowerShop = new Flower();

        Pageable pageable = PageRequest.of(page, 5);
        Page<Flower> flowerPage = flowerService.getAllFlowers(name, flower, pricefrom, priceto, pageable);

        model.addAttribute("flowers", flowerPage.getContent());
        model.addAttribute("flower", flowerShop);
        model.addAttribute("name", name);
        model.addAttribute("pricefrom", pricefrom);
        model.addAttribute("priceTo", priceto);
        model.addAttribute("flowerf", flower);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flowerPage.getTotalPages());

        List<Flower> topflowers = flowerService.getTopFlowers();
        model.addAttribute("topflowers", topflowers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/flowers/filter");
        if (name != null && !name.isEmpty()) uriBuilder.queryParam("name", name);
        if (pricefrom != null) uriBuilder.queryParam("pricefrom", pricefrom);
        if (priceto != null) uriBuilder.queryParam("priceto", priceto);
        if (flower != null && !flower.isEmpty()) uriBuilder.queryParam("flowerf", flower);
        model.addAttribute("filterUrl", uriBuilder.build().toString());
//        System.out.println(model);
        return "flowers";
    }

    @PostMapping("/authenticateTheUser")
    public String authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null) {
            String storedPassword = userDetails.getPassword();
            if (password.equals(storedPassword)) {
                model.addAttribute("username", username);
                return "redirect:/Flowers";
            }
        }

        return "flowers";
    }
}
