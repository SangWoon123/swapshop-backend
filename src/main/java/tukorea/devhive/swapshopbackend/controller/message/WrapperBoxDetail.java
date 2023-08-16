package tukorea.devhive.swapshopbackend.controller.message;

import lombok.Data;

@Data
public class WrapperBoxDetail<T> {
    private T messageDetail;

    public WrapperBoxDetail(T messageDetail) {
        this.messageDetail = messageDetail;
    }


}
