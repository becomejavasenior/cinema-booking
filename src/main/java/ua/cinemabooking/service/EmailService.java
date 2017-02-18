package ua.cinemabooking.service;

public interface EmailService {

    void sendMessage(String textMessage, String email);

    void sendMessage(String textMessage, String subject, String email);
}
