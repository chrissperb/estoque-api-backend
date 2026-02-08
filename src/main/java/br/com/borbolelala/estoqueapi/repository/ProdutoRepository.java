package br.com.borbolelala.estoqueapi.repository;

import br.com.borbolelala.estoqueapi.model.Produto;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

    List<Produto> findByCategoriaIgnoreCase(String categoria);

    @Aggregation(pipeline = {
            "{ '$project': { 'totalItem': { '$multiply': ['$preco', '$quantidade'] } } }",
            "{ '$group': { '_id': null, 'totalGeral': { '$sum': '$totalItem' } } }"
    })
    Double obterValorTotalEstoque();

}