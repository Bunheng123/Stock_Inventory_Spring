package com.setec.stock_inventory.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequestDto {

    @NotBlank(message = "Shipping address is required")
    @Size(max = 255, message = "Shipping address must be less than 255 characters")
    private String shippingAddress;

    @Size(max = 500, message = "Customer note must be less than 500 characters")
    private String customerNote;
}
