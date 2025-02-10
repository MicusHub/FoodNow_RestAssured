package foodnow.dto.login;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class TokenDTO {
    private String refreshToken;
    private String accessToken;
}
