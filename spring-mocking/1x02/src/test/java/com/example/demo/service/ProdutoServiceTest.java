package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        Long produtoId = 1L;
        Produto produtoEsperado = new Produto(produtoId, "Produto Teste", 100.0);
        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoEsperado));

        Produto produtoRetornado = produtoService.buscarPorId(produtoId);

        assertNotNull(produtoRetornado);
        assertEquals(produtoEsperado.getId(), produtoRetornado.getId());
        assertEquals(produtoEsperado.getNome(), produtoRetornado.getNome());
        assertEquals(produtoEsperado.getPreco(), produtoRetornado.getPreco());

        Mockito.verify(produtoRepository, Mockito.times(1)).findById(produtoId);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        Long produtoId = 99L;
        Mockito.when(produtoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(produtoId);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(produtoId);
    }
}