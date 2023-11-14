package controller;
import org.springframework.data.domain.Page;
import entities.User;
import exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;
import payloads.NewUserDTO;
import java.io.IOException;


@RestController
@RequestMapping ("/user")
public class UserController {

        @Autowired
        private UserService userService;


    @GetMapping("/{id}")
    User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return userService.getAllUsers(page, size);
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

    @PatchMapping("upload/avatar/{id}")
    User uploadImg(@RequestParam("avatar") MultipartFile body, @PathVariable long id) throws IOException {
        System.out.println(body.getOriginalFilename());
        return userService.uploadImg(body, id);
    }

    @GetMapping("/me")
    public UserDetails getLoggedProfile(@AuthenticationPrincipal UserDetails loggedUser) {
        return loggedUser;
    }

    @PutMapping("/me")
    public UserDetails updateLoggedProfile(@AuthenticationPrincipal User loggedUser, @RequestBody User user) {
        return userService.findUserByIdAndUpdate(loggedUser.getId(), user);
    }

    @DeleteMapping("/me")//  204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getProfile(@AuthenticationPrincipal User loggedUser) {
        userService.findUserByIdAndDelete(loggedUser.getId());
    }
}
