package study.datajpa.exception;

public class NotMemberException extends IllegalArgumentException{

    public NotMemberException() {
        super("member not found");
    }

}
