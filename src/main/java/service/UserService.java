package service;
import entities.User;
import exception.BadRequestException;
import exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import payloads.NewUserDTO;
import java.io.IOException;

@Service
public class UserService {
@Autowired
    private UserRepository userRepository;


    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public User findUserByIdAndUpdate(long id, User user) {
        User foundUser = this.findUserById(id);
        foundUser.setUsername(user.getUsername());
        foundUser.setSurname(user.getSurname());
        foundUser.setEmail(user.getEmail());
        return userRepository.save(foundUser);
    }

    public void findUserByIdAndDelete(long id) {
        User foundUser = this.findUserById(id);
        userRepository.delete(foundUser);
    }

    public UserRepository saveUser(NewUserDTO userDTO) throws IOException {
        userRepository.findByEmail(userDTO.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + "esiste già");
        });


        userRepository.findByUsername(userDTO.username()).ifPresent(user -> {
            throw new BadRequestException(
                    "Lo username " + user.getUsername() + "esiste già");
        });

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setName (userDTO.name());
        newUser.setSurname(userDTO.surname());
        newUser.setEmail(userDTO.email());
        return (UserRepository) userRepository.save(newUser); //<---- da rivedere
    }

}
