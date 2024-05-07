package live.databo3.front.auth.exception;

public class TokenExpiredException extends RuntimeException{
    private static final String MESSAGE = "토큰이 만료됨.";

    public TokenExpiredException() {
        super(MESSAGE);
    }
}
