package co.ucentral.BackEnd_UniRoutine.controlador;

import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Evento;
import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Tarea;
import co.ucentral.BackEnd_UniRoutine.persistencia.entidades.Usuario;
import co.ucentral.BackEnd_UniRoutine.servicios.EventoServicio;
import co.ucentral.BackEnd_UniRoutine.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/eventos")
public class EventoControlador {
    @Autowired
    EventoServicio eventoServicio;
    @Autowired
    UsuarioServicio usuarioServicio;

    // End point para obtener todos los eventos por usuario
    @PostMapping("/usuario")
    public List<Evento> obtenerEventosPorUsuario(@RequestBody Map<String, Integer> payload) {
        int idUsuario = payload.get("idUsuario");
        Usuario usuario = usuarioServicio.consultarUsuarioPorId(idUsuario);
        usuario.setId_usuario(idUsuario);
        return eventoServicio.obtenerEventosPorUsuarioOrdenadosPorPrioridad(usuario);
    }
    // End point para crear un evento
    @PostMapping("/crear")
    public ResponseEntity<String> crearEvento(@RequestBody Evento evento) {

        Evento registro = eventoServicio.consultarEventoPorId(evento.getId_evento());
        if (registro != null) {
            evento.setFechaCreacion(LocalDateTime.now());
            BeanUtils.copyProperties(evento, registro);
            eventoServicio.guardarEvento(registro);
            return ResponseEntity.ok("Evento actualizado con éxito: " + evento.getId_evento());
        }else {
            evento.setId_evento(usuarioServicio.generarId());
            evento.setFechaCreacion(LocalDateTime.now());
            eventoServicio.guardarEvento(evento);
            return ResponseEntity.ok("Evento creada con éxito: " + evento.getId_evento());
        }
    }
    // End point para eliminar una evento
    @PostMapping("/eliminar")
    public ResponseEntity<Void> eliminarEvento(@RequestBody Evento eventoRequest) {
        eventoServicio.eliminarEvento(eventoRequest.getId_evento());
        return ResponseEntity.noContent().build();
    }
}

