package is.recruit.mycroft.spring.subjects.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, name = "seat_x_count")
    private int seatXCount;

    @Column(nullable = false, name = "seat_y_count")
    private int seatYCount;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;


    @Builder
    private Theater(String name, int seatXCount, int seatYCount) {
        this.name = name;
        this.seatXCount = seatXCount;
        this.seatYCount = seatYCount;
    }


}
