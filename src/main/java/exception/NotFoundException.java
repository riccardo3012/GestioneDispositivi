package exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException (long id){
        super ("Il seguente id"+id+"non esiste");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
