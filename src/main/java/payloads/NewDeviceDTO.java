package payloads;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import utils.StatoDispositivo;

public record NewDeviceDTO(
        @NotEmpty(message = " nome obbligatorio")
        @Size(min = 1, max = 50, message = "Il nome deve avere minimo 1 carattere e massimo 50")
        String name,
        StatoDispositivo stato,
        @NotNull(message = "Id è obbligatorio")
        long userID) {

}
