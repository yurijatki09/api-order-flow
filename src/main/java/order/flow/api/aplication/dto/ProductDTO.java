package order.flow.api.aplication.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.flow.api.domain.model.Category;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude
public class ProductDTO implements Serializable {
    
    private UUID id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Category categoria;

    private Integer quantidade;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;
}
