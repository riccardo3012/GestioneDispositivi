package entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name="user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    @Column
    private String name;
    @Column
    private String surname;
    private String email;
    private String password;
    private String avatar;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();

}
