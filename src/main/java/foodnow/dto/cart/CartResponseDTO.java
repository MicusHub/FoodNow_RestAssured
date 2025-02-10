package foodnow.dto.cart;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder

public class CartResponseDTO {
    private List<CartItemDTO> items;
}
