package order.flow.api.aplication.dto;
import java.math.BigDecimal;

public class UserTicketDTO {
    private Long userId;
    private String userNome;
    private BigDecimal ticketMedio;

    public UserTicketDTO(Long userId, String userNome, BigDecimal ticketMedio) {
        this.userId = userId;
        this.userNome = userNome;
        this.ticketMedio = ticketMedio;
    }

    public Long getUserId() { return userId; }
    public String getUserNome() { return userNome; }
    public BigDecimal getTicketMedio() { return ticketMedio; }
}
