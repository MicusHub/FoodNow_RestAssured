package foodnow.dto.order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class OrderPaymentResponseDTO {
    private int id;
    private String address;
    private String deliveryTime;
    private String orderStatus;
    private String paymentMethod;
    private String paymentUrl;
}
