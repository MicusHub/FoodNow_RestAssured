package foodnow.dto.login;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder

public class RoleDTO {
    private int id;
    private String title;
    private String authority;
}
