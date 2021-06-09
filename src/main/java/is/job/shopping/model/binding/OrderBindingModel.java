package is.job.shopping.model.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderBindingModel {
    private Long quantity;

    public OrderBindingModel() {
    }

    @NotNull
    @Min(value = 1 , message = "Quantity cannot be less than 1")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
