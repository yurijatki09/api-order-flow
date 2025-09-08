package order.flow.api.aplication.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDTO implements Serializable {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductRequest {
        private UUID productId;
        private Integer quantidade;
    }

    private List<ProductRequest> produtos;
}
