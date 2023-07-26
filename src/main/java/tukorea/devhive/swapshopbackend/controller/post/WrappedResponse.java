package tukorea.devhive.swapshopbackend.controller.post;

import lombok.Data;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;

import java.util.List;

@Data
public class WrappedResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public WrappedResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}
