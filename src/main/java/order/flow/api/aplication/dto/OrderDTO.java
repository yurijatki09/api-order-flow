package order.flow.api.aplication.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.flow.api.domain.model.OrderProduct;
import order.flow.api.domain.model.Status;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude
public class OrderDTO implements Serializable {

    private UUID id;

    @JsonIgnore
    private UUID userId;

    private String userNome;

    private List<OrderProductDTO> produtos;

    private Integer quantidade;

    private BigDecimal total;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String mensagem;

    private BigDecimal precoTotal;
}
