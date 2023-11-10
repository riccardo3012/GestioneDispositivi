package payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = " nome obbligatorio!")
        @Size(min = 1, max = 50, message = "Il nome deve avere minimo 1 carattere e massimo 50")
        String name,
        @NotEmpty(message = "cognome obbligatorio!")
        String surname,
        @NotEmpty(message = "username  obbligatorio!")
        String username,
        @NotEmpty(message = "email obbligatoria!")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email non valida")
        String email) {
        public NewUserDTO{}
}
