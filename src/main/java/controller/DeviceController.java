package controller;
import entities.Device;
import exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import payloads.NewDeviceDTO;
import service.DeviceService;
import java.io.IOException;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/{id}")
    Device findDeviceById(@PathVariable long id) {
        return deviceService.findDeviceById(id);
    }

    @GetMapping
    Page<Device> getAllDevice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return deviceService.getAllDevice(page, size);
    }

    @PutMapping("/{id}")
    Device findDeviceByIdAndUpdate(@PathVariable long id, @RequestBody NewDeviceDTO newDeviceDTO) {
        return deviceService.findDeviceByIdAndUpdate(id, newDeviceDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void findDeviceByIdAndDelete(@PathVariable long id) {
        deviceService.findDeviceByIdAndDelete(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Device save(@RequestBody @Validated NewDeviceDTO deviceDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return deviceService.save(deviceDTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}