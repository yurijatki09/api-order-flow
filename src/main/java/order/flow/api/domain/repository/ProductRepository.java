package order.flow.api.domain.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.dto.ProductListDTO;
import order.flow.api.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT new order.flow.api.aplication.dto.ProductListDTO( " +
       "p.id, p.nome, p.descricao, p.preco, p.categoria, p.quantidade, p.dataCriacao, p.dataAtualizacao) " +
       "FROM Product p " +
       "WHERE p.ativo = true " +
       "ORDER BY p.nome ASC")
    List<ProductListDTO> getProducts();

    @Query(value = "SELECT p.* FROM products p WHERE p.id = :id AND p.ativo = true", nativeQuery = true)
    Product findByActiveId(UUID id);

}
