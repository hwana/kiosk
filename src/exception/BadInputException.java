package exception;

public class BadInputException extends Exception {
    public BadInputException() {
        super("잘못된 입력입니다! 화면에 출력된 숫자를 입력해주세요!");
    }
}
