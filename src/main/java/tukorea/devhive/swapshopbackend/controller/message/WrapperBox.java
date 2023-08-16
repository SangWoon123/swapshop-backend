package tukorea.devhive.swapshopbackend.controller.message;

import lombok.Data;

@Data
public class WrapperBox<T> {
    private T messageBox;

    public WrapperBox(T messageBox) {
        this.messageBox = messageBox;
    }

}
