package toy.project.exception;

/* RuntimeException을 상속 받는 OutofStockException을 생성함 */
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String messsage) {
        super((messsage));
    }
}
