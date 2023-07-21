package tukorea.devhive.swapshopbackend.model.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.category.PostCategory;
import tukorea.devhive.swapshopbackend.model.dao.TradePeriod;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;

    @JsonIgnore
    private Login login;

    private String title;
    private String content;
    private int price;
    private String location;
    private TradePeriod desiredTime;
    private TradeStatus status;
    private int views;
    private List<PostCategory> categories;

    @Builder
    public PostDTO(Long id, Login login, String title, String content, int price, String location, TradePeriod desiredTime, TradeStatus status, int views, List<PostCategory> categories) {
        this.id = id;
        this.login = login;
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.desiredTime = desiredTime;
        this.status = status;
        this.views = views;
        this.categories = categories;
    }

    public Post toEntity(){
        return Post.builder()
                .id(id)
                .login(login)
                .title(title)
                .content(content)
                .price(price)
                .location(location)
                .desiredTime(desiredTime)
                .status(status)
                .views(views)
                .build();
    }


}
