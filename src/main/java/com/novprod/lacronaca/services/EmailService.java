package com.novprod.lacronaca.services;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
