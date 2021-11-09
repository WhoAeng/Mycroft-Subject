package is.recruit.mycroft.spring.subjects.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotNull
    int theaterId;
    @NotNull
    int seatId;
    @NotNull
    int price;
}
