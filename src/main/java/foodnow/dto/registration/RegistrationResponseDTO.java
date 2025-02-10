package foodnow.dto.registration;

import foodnow.dto.login.RoleDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class RegistrationResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private RoleDTO[] roles;
}
