package service;
import entities.Device;
import entities.User;
import exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public Device findDeviceById(long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Device> getAllDevice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return deviceRepository.findAll(pageable);
    }

    public Device findDeviceByIdAndUpdate(long id, NewDeviceDTO deviceDTO) {
        User foundUser = UserService.findUserById(deviceDTO.userID());
        Device foundDevice = this.findDeviceById(id);
        foundDevice.setTipoDispositivo(TipoDispositivo.valueOf(deviceDTO.name()));
        foundDevice.setStatoDispositovo(deviceDTO.stato());
        foundDevice.setUser(foundUser);
        return deviceRepository.save(foundDevice);
    }

    public void findDeviceByIdAndDelete(long id) {
        Device foundDevice = this.findDeviceById(id);
        deviceRepository.delete(foundDevice);
    }

    public Device save(NewDeviceDTO body) throws IOException {
        User user = UserService.findUserById(body.userID());
        Device device = new Device();
        device.setTipoDispositivo(TipoDispositivo.valueOf(body.name()));
        device.setStatoDispositovo(body.stato());
        if (device.getStatoDispositovo() == StatoDispositivo.ASSEGNATO) {
            device.setUser(user);
        } else {
            device.setUser(null);
        }
        return deviceRepository.save(device);
       }
    }









