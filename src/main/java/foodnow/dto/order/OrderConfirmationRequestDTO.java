package foodnow.dto.order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class OrderConfirmationRequestDTO {
    private int id;
    private String address;
    private String deliveryTime;
    private String paymentMethod;
}
