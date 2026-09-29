package com.aparkautepino.aparkautepino.Model.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Usuario;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioRepository;

@Service 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void crearUsuario(Usuario usuario){

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public Page<Usuario> obtenerUsuarios(int pagina){
        Pageable pageable = PageRequest.of(pagina, 20, Sort.by("nombre"));
        return usuarioRepository.findAll(pageable);
    }
}
