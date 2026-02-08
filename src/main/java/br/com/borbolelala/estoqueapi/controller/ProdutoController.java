package br.com.borbolelala.estoqueapi.controller;

import br.com.borbolelala.estoqueapi.model.Produto;
import br.com.borbolelala.estoqueapi.repository.ProdutoRepository;
import br.com.borbolelala.estoqueapi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable String id) {
        return produtoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable String id, @RequestBody Produto produto) {
        return produtoService.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id) {
        produtoService.deletar(id);
    }

    @PatchMapping("/{id}/movimentar")
    public Produto movimentarEstoque(@PathVariable String id, @RequestBody Map<String, Integer> payload) {
        Integer quantidade = payload.get("quantidade");
        if (quantidade == null) {
            throw new IllegalArgumentException("O corpo da requisição deve conter a chave 'quantidade'.");
        }
        return produtoService.movimentarEstoque(id, quantidade);
    }

    @GetMapping("/valor-total")
    public ResponseEntity<Double> getValorTotal() {
        Double total = produtoRepository.obterValorTotalEstoque();
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

}