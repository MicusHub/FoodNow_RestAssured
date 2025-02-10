package foodnow.dto.product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class ProductRequestDTO {
    private String title;
    private double price;
    private String productCode;
    private String minQuantity;
    private String description;
    private String photoLink;
}
