package service;

import entities.User;
import exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payloads.UserLoginDTO;
import security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    public String authUser(UserLoginDTO userLogin) {

        User newUser = userService.findByEmail(userLogin.email());
        if (newUser.getPassword().equals(userLogin.email())) {
            return jwtTools.createToken(newUser);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }

    }
}