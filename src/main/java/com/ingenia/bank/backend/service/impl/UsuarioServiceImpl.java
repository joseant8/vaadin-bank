package com.ingenia.banca.service.impl;

import com.ingenia.banca.model.Usuario;
import com.ingenia.banca.repository.UsuarioRepository;
import com.ingenia.banca.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    UsuarioRepository usuarioRepositorio;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Optional<Usuario> obtenerUsuarioById(Long id) {
        return usuarioRepositorio.findById(id);
    }

    @Override
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario) {
        // miramos si ya existe un usuario con el 'username' indicado
        if(usuarioRepositorio.existsByUsername(usuario.getUsername())){
            return Optional.empty();
        }else{
            Usuario usuarioCreado = usuarioRepositorio.save(usuario);
            return Optional.of(usuarioCreado);
        }
    }
}
