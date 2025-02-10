package foodnow.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AuthResponseDTO {
    private TokenDTO token;
    //public String token;
    private UserDTO user;

}
