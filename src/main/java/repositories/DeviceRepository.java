package repositories;

import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<User, Long> {
}


