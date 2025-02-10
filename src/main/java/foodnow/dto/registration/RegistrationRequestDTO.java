package foodnow.dto.registration;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder
public class RegistrationRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
