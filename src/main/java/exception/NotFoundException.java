package exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException (String id){
        super ("Il seguente id"+id+"non esiste");
    }
}
