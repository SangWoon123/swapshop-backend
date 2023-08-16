package tukorea.devhive.swapshopbackend.controller.message;

import lombok.Data;

@Data
public class WrapperMessage<T> {
    private boolean success;
    private T data;
    private String message;

    public WrapperMessage(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}
