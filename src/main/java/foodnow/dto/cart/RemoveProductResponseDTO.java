package foodnow.dto.cart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RemoveProductResponseDTO {
    private String message;
}
