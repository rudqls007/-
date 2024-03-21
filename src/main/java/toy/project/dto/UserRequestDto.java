package toy.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRequestDto {

    private String loginId;
    private String email;
}
