package rw.ac.ilpd.mis.auth.exception;

public class AccountNotEnabledException extends RuntimeException{
    public AccountNotEnabledException(String s){
        super(s);
    }
}
