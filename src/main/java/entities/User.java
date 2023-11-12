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
    @Column(name = "user_id")
    private long id;
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    private String email;
    private String password;
    private String avatar;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();

}
