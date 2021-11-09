package is.recruit.mycroft.spring.subjects.model.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
