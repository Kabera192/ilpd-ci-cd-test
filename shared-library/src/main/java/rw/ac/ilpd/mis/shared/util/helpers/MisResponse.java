package rw.ac.ilpd.mis.shared.util.helpers;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 11/07/2025
 */

public class MisResponse<T> {

    private boolean success;
    private String message;
    private T result;

    public MisResponse() {}

    public MisResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MisResponse(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
