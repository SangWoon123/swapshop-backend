package tukorea.devhive.swapshopbackend.model.dao.post;

import lombok.*;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.dao.BaseTimeEntity;
import tukorea.devhive.swapshopbackend.model.dao.TradePeriod;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Login login;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "post_content",nullable = false)
    private String content;

    @Column(name = "price")
    private int price;

    @Column(name = "desired_location")
    private String location;

    @Column(name = "desired_time_range")
    @Embedded
    private TradePeriod desiredTime; // @Embedded 타입으로 변경 필요

    @Column(name = "status")
    private TradeStatus status;

    @Column(name = "views",columnDefinition = "integer default 0", nullable = false)
    private int views;

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Image> images=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    private List<Favorite> favorites=new ArrayList<>();

    @Builder
    public Post(Long id, Login login, String title, String content, int price, String location, TradePeriod desiredTime, TradeStatus status, int views, List<Image> images, Category category) {
        this.id = id;
        this.login = login;
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.desiredTime = desiredTime;
        this.status = status;
        this.views = views;
        this.images = images;
        this.category = category;
    }

    public void update(String title, String content, int price, String location, TradePeriod desiredTime, TradeStatus status, int views, List<Image> images) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.desiredTime = desiredTime;
        this.status = status;
        this.views = views;
        changeImage(images);
    }

    public void changeImage(List<Image> updatedImages) {
        this.images.clear();
        this.images.addAll(updatedImages);
    }



    //제목, 내용, 가격, 희망거래장소, 희망거래시간, 거래상태, 조회수, 작성시간, 수정시간
}
