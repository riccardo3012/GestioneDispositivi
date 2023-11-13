package controller;

import entities.User;
import exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import payloads.LoginSeccessfullDTO;
import payloads.NewUserDTO;
import payloads.UserLoginDTO;
import service.AuthService;
import service.UserService;

import java.io.IOException;

public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginSeccessfullDTO login(@RequestBody UserLoginDTO userLogin) {
        return new LoginSeccessfullDTO(authService.authUser(userLogin));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    User saveUser(@RequestBody @Validated NewUserDTO userDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return userService.saveUser(userDTO);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

}

}
