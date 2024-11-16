package com.jdbc.crud.controllers;

import com.jdbc.crud.model.Sales;
import com.jdbc.crud.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @Autowired
    private SalesRepository repository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("sales", repository.findAll());
        return "index";
    }

    @RequestMapping(value = "/new")
    public String form(Model model) {
        model.addAttribute("sales", new Sales());
        return "form";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("sales") Sales sales) {
        if (sales.getId() == 0) {
            repository.save(sales); // Insert new record
        } else {
            repository.update(sales); // Update existing record
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("sales", repository.findById(id));
        return "form";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // New method for displaying details
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Sales sales = repository.findById(id);
        if (sales != null) {
            model.addAttribute("sales", sales); // Add sales details to the model
            return "detail"; // View to show details
        } else {
            return "redirect:/"; // If no data found, redirect to the index page
        }
    }
}
