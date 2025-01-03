package co.ucentral.BackEnd_UniRoutine.servicios;

import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Usuario;
import co.ucentral.BackEnd_UniRoutine.persistencia.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioServicio {
     UsuarioRepositorio usuarioRepositorio;

     //crear/editar usuario
        public void GuardarUsuario(Usuario usuario){
            usuarioRepositorio.save(usuario);
        }
     //eliminar un usuario
        public void eliminarUsuario(int id){
            usuarioRepositorio.deleteById(id);
        }
      //consultar todos los usuarios
     public Iterable<Usuario> consultarUsuarios(){
           return usuarioRepositorio.findAll();
        }
    //consultar usuario por id
    public Usuario consultarUsuarioPorId(int id){
        return usuarioRepositorio.findById(id).orElse(null);
    }
    //generar Id random para el usuario
    public int generarId(){
        return (int) (Math.random()*10000);
    }
    public Usuario validarLogin(String correo, String contrasena){
            Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElse(null);
            if(usuario != null){
                if(usuario.getContrasena().equals(contrasena)){
                    return usuario;
                }
            }
            return null;
    }

}
