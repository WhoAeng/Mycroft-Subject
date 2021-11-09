package is.recruit.mycroft.spring.subjects.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "favorite_seat")
public class FavoriteSeat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteSeatId;

    @Column(unique = true)
    private String code;

    private String name;

    @Builder
    public FavoriteSeat(String code, String name){
        this.code = code;
        this.name = name;
    }
}
