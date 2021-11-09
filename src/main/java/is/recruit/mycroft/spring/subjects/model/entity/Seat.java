package is.recruit.mycroft.spring.subjects.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Column(nullable = false)
    private int theaterId;

    @Column(name = "seat_x")
    private int seatX;
    @Column(name = "seat_y")
    private int seatY;
    private String seatName;

    private int bookingId;

    private int surcharge;

    @Builder
    private Seat(int theaterId, int seatX, int seatY, int surcharge) {
        this.theaterId = theaterId;
        this.seatX = seatX;
        this.seatY = seatY;
        createSeatName(seatX,seatY);
        this.surcharge = surcharge;
    }

    //아스키 코드 65-90 A-Z 매칭
    private void createSeatName(int seatX, int seatY){
        int asciiCodeY = seatY+64;
        if (64 < asciiCodeY && asciiCodeY < 91){
            this.seatName = String.valueOf((char) asciiCodeY) + seatX;
        }else {
            System.out.println("범주를 벗어나는 숫자입니다");
        }
    }







}
