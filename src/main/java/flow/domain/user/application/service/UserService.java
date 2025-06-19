package flow.domain.user.application.service;


import flow.domain.user.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public interface UserService {

    String getLoginLink();

    User login(String code, HttpServletResponse response, HttpServletRequest request) throws IOException;

    void logout(HttpSession httpSession);
}