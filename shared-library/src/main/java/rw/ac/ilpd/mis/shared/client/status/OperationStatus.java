package rw.ac.ilpd.mis.shared.client.status;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2025
 */
public class OperationStatus {
    private boolean success;
    private String message;

    public OperationStatus() {
        success = false;
        message = "Not performed";
    }

    public OperationStatus(boolean success) {
        this();
        this.success = success;
    }

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

    @Override
    public String toString() {
        return "{" +
                "\"success\":\"" + success + "\"" + ", " +
                "\"message\":" + (message == null ? "null" : "\"" + message + "\"") +
                "}";
    }
}