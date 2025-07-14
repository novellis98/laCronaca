package com.novprod.lacronaca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novprod.lacronaca.Repositories.RoleRepository;
import com.novprod.lacronaca.models.CareerRequest;
import com.novprod.lacronaca.models.Role;

@Controller
@RequestMapping("/operations")
public class OperationController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/career/request")
    public String careerRequestCreate(Model viewModel) {
        viewModel.addAttribute("title", "Inserisci la tua richiesta");
        viewModel.addAttribute("careerRequest", new CareerRequest());

        List<Role> roles = roleRepository.findAll();
        // elimino la possibilità di scegliere ruolo user dalla select del form
        roles.removeIf(e -> e.getName().equals("ROLE_USER"));
        viewModel.addAttribute("roles", roles);
        return "career/requestForm";
    }
}
