package entities;
import jakarta.persistence.*;
import lombok.*;
import utils.StatoDispositivo;
import utils.TipoDispositivo;

@Entity
@Table(name="device")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Device {
    @Id
    @GeneratedValue
    @Column(name = "device_id")
    private long id;
    private TipoDispositivo TipoDispositivo;
    @Enumerated(EnumType.STRING)
    private StatoDispositivo statoDispositovo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;


}
