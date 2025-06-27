package com.novprod.lacronaca.services;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novprod.lacronaca.dtos.UserDto;
import com.novprod.lacronaca.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void saveUser(UserDto userDto, RedirectAttributes redirectAttributes, HttpServletRequest request,
            HttpServletResponse response);

    User findUserByEmail(String email);
}
