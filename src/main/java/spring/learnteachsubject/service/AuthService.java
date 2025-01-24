package spring.learnteachsubject.service;

import spring.learnteachsubject.model.User;

import java.util.List;

public interface AuthService {

    User login(String email, String password);

    List<User> findAll();

}
