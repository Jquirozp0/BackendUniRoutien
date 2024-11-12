package co.ucentral.BackEnd_UniRoutine.controlador;

import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Tarea;
import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Usuario;
import co.ucentral.BackEnd_UniRoutine.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioControlador {
    @Autowired
    UsuarioServicio usuarioServicio;

    // End point para obtener todos los usuarios
    @PostMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        Iterable<Usuario> usuarios = usuarioServicio.consultarUsuarios();
        return ResponseEntity.ok((List<Usuario>) usuarios);
    }
    // End point para crear/editar usuario
    @PostMapping("/guardar")
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario registro = usuarioServicio.consultarUsuarioPorId(usuario.getId_usuario());
        if (registro != null) {
            BeanUtils.copyProperties(usuario, registro);
            usuarioServicio.GuardarUsuario(registro);
            return ResponseEntity.ok("Usuario actualizado con éxito: " + usuario.getId_usuario());
        }else {
            usuario.setId_usuario(usuarioServicio.generarId());
            usuarioServicio.GuardarUsuario(usuario);
            return ResponseEntity.ok("Usuario creado con éxito: " + usuario.getId_usuario());
        }
    }
    // End point para eliminar usuario
    @PostMapping("/eliminar")
    public ResponseEntity<Void> eliminarUsuario(@RequestBody int id) {
        usuarioServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    // End point para validar si usuario existe en la base de datos
    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarUsuario(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String contrasena = datos.get("contrasena");

        Usuario usuario = usuarioServicio.consultarUsuarioPorCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}
