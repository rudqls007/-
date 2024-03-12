package toy.project.dto;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class UserDtoTest {

    @Test
    public void test() {

        UserDto userDto = new UserDto();
        userDto.setName("Lee");
        userDto.setAge(20);

        System.out.println("userDto = " + userDto);

    }
}
