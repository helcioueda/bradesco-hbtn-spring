package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Long usuarioId = 1L;
        Usuario usuarioEsperado = new Usuario(usuarioId, "João", "joao@email.com");
        Mockito.when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioEsperado));

        Usuario usuarioRetornado = usuarioService.buscarUsuarioPorId(usuarioId);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioEsperado.getId(), usuarioRetornado.getId());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long usuarioId = 99L;
        Mockito.when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(usuarioId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(usuarioId);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario(null, "Maria", "maria@email.com");
        Usuario usuarioSalvo = new Usuario(1L, "Maria", "maria@email.com");

        Mockito.when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(resultado.getId());
        assertEquals(usuarioSalvo.getNome(), resultado.getNome());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuarioParaSalvar);
    }
}