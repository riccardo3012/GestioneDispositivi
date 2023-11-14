package exception;
import lombok.Getter;
import org.springframework.validation.ObjectError;
import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{
    private List<ObjectError> errorList;
     public BadRequestException(String message){
         super(message);

     }
    public BadRequestException(List<ObjectError> error){
        super("Presente bug nel body");
        this.errorList = error;

    }
}
