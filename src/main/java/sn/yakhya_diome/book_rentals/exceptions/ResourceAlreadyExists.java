package sn.yakhya_diome.book_rentals.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message){
        super(message);
    }
}
