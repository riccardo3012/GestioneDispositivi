package service;
import entities.Device;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payloads.NewDeviceDTO;
import repositories.DeviceRepository;
import utils.StatoDispositivo;
import utils.TipoDispositivo;
import java.io.IOException;
import service.UserService;
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private User user;


    public Device save(NewDeviceDTO body) throws IOException {

        User u = UserService.findById(body.userID());
        Device d = new Device();




    }








}
