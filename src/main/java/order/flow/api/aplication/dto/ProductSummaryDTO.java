package order.flow.api.aplication.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import order.flow.api.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
public class ProductSummaryDTO {

    @JsonIgnore
    private UUID id;
    
    private String nome;

    private BigDecimal preco;

    private Category categoria;
}