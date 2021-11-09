package is.recruit.mycroft.spring.subjects.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    private String description;

    @Builder
    private Movie(String title, int price, String description){
        this.title = title;
        this.price = price;
        this.description = description;
    }

}
