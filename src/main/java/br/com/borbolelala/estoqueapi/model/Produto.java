package br.com.borbolelala.estoqueapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import lombok.Data;

@Document(collection = "produtos")
@Data
public class Produto {

    @Id
    private String id;
    private String nome;
    private String categoria;
    private Integer quantidade;
    private BigDecimal preco;
}