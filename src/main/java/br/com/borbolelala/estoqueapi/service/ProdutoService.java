package br.com.borbolelala.estoqueapi.service;

import br.com.borbolelala.estoqueapi.exception.ProdutoNotFoundException;
import br.com.borbolelala.estoqueapi.model.Produto;
import br.com.borbolelala.estoqueapi.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(String id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + id + " não encontrado."));
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(String id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setCategoria(produtoAtualizado.getCategoria());
        produtoExistente.setPreco(produtoAtualizado.getPreco());

        return produtoRepository.save(produtoExistente);
    }

    public void deletar(String id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto com ID " + id + " não encontrado para exclusão.");
        }
        produtoRepository.deleteById(id);
    }

    public Produto movimentarEstoque(String id, int quantidadeParaAdicionar) {
        Produto produto = buscarPorId(id);

        int novaQuantidade = produto.getQuantidade() + quantidadeParaAdicionar;
        if (novaQuantidade < 0) {
            // Lançar uma exceção específica seria ainda melhor, mas por enquanto uma geral resolve.
            throw new IllegalArgumentException("A movimentação resultaria em estoque negativo.");
        }

        produto.setQuantidade(novaQuantidade);
        return produtoRepository.save(produto);
    }
}