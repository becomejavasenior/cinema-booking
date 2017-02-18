package ua.cinemabooking.service;

public interface EmailService {

    void init();

    void sendMessage(String textMessage, String email);

    void sendMessage(String textMessage, String subject, String email);
}
