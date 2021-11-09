package is.recruit.mycroft.spring.subjects;

import is.recruit.mycroft.spring.subjects.model.entity.*;
import is.recruit.mycroft.spring.subjects.repository.FavoriteSeatRepository;
import is.recruit.mycroft.spring.subjects.repository.MovieRepository;
import is.recruit.mycroft.spring.subjects.repository.SeatRepository;
import is.recruit.mycroft.spring.subjects.repository.TheaterRepository;
import is.recruit.mycroft.spring.subjects.service.TheaterService;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitialize {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;
    private final FavoriteSeatRepository favoriteSeatRepository;
    private final TheaterService theaterService;

    @PostConstruct
    private void initAll() {
        userInit();
        movieInit();
        theaterInit();
        seatInit();
        roleInit();
        userRoleInit();
        FavoriteSeatInit();
        userFavoriteSeatInit();
    }

    private void userInit() {
        userService.saveUser(User.builder().username("booker1").password("booker1").build());
        userService.saveUser(User.builder().username("booker2").password("booker2").build());
        userService.saveUser(User.builder().username("booker3").password("booker3").build());
    }

    private void movieInit() {
        movieRepository.save(Movie.builder().title("이터널스").price(10000).description("마블영화").build());
        movieRepository.save(Movie.builder().title("듄").price(11000).description("스릴러영화").build());
    }

    private void theaterInit() {
        theaterRepository.save(Theater.builder().name("상영관A").seatXCount(10).seatYCount(8).build());
        theaterRepository.save(Theater.builder().name("상영관B").seatXCount(8).seatYCount(7).build());
        theaterService.addMovieToTheater(1L,1L);
        theaterService.addMovieToTheater(2L,2L);
    }

    private void seatInit() {
        List<Theater> theaterList = theaterRepository.findAll();
        for (Theater theater : theaterList) {
            int x = theater.getSeatXCount();
            int y = theater.getSeatYCount();

            int surchargeNum = y / 3;

            for (int i = 1; i <= x; i++) {
                for (int j = 1; j <= y; j++) {
                    int surcharge = 0;

                    if (surchargeNum < j) {
                        surcharge += 1000;
                        if (y - surchargeNum < j) {
                            surcharge += 1000;
                        }
                    }
                    seatRepository.save(Seat.builder()
                            .theaterId(theater.getTheaterId().intValue()).seatX(i).seatY(j).surcharge(surcharge).build());
                }
            }
        }
    }

    private void roleInit() {
        userService.saveRole(Role.builder().roleName("USER").build());
        userService.saveRole(Role.builder().roleName("ADMIN").build());
    }

    private void userRoleInit() {
        userService.addRoleToUser("booker1", "ADMIN");
        userService.addRoleToUser("booker1", "USER");
        userService.addRoleToUser("booker2", "USER");
        userService.addRoleToUser("booker3", "USER");
    }

    private void FavoriteSeatInit() {
        favoriteSeatRepository.save(FavoriteSeat.builder().code("FL").name("앞열 좌측").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("FM").name("앞열 중간").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("FR").name("앞열 우측").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("ML").name("중간열 좌측").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("MM").name("중간열 중간").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("MR").name("중간열 우측").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("RL").name("뒷열 좌측").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("RM").name("뒷열 중간").build());
        favoriteSeatRepository.save(FavoriteSeat.builder().code("RR").name("뒷열 우측").build());
    }

    private void userFavoriteSeatInit(){
//        userService.addFavoriteSeatToUser("booker1","MM");
    }

}
