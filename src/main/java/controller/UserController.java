package controller;
import org.springframework.data.domain.Page;
import entities.User;
import exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import payloads.NewUserDTO;
import java.io.IOException;

@RestController
@RequestMapping ("/user")
public class UserController {

        @Autowired
        private UserService userService;

    @GetMapping
    Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return userService.getAllUsers(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User save(@RequestBody @Validated NewUserDTO userDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return (User) userService.saveUser(userDTO);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    @GetMapping("/{id}")
    User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    User findUserByIdAndUpdate(@PathVariable long id, @RequestBody User user) {
        return userService.findUserByIdAndUpdate(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void findUserByIdAndDelete(@PathVariable long id) {
        userService.findUserByIdAndDelete(id);
    }


}
