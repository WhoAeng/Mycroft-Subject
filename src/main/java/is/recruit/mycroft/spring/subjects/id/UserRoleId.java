package is.recruit.mycroft.spring.subjects.id;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    private Long user;
    private Long role;

}
