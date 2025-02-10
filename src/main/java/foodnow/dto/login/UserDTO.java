package foodnow.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private RoleDTO[] roles;
}
