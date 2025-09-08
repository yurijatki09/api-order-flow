package order.flow.api.aplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import order.flow.api.domain.model.Category;

public class ProductListDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Category categoria;
    private Integer quantidade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ProductListDTO(UUID id, String nome, String descricao, BigDecimal preco, Category categoria,
                          Integer quantidade, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public Category getCategoria() { return categoria; }
    public Integer getQuantidade() { return quantidade; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}