package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import com.RisosuIT.Pokedex.Estado.EstadoCargaAplicacion;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorCarga {

    private final ServicioPokemon servicioPokemon;
    private final EstadoCargaAplicacion estadoCarga;

    public ControladorCarga(ServicioPokemon servicioPokemon, EstadoCargaAplicacion estadoCarga) {
        this.servicioPokemon = servicioPokemon;
        this.estadoCarga = estadoCarga;
    }

    @PostMapping("/start-load")
    public ResponseEntity<Void> iniciarCarga() {
        if (!estadoCarga.isTerminado() && estadoCarga.getProgresoActual() > 0) {
            return ResponseEntity.status(409).build(); 
        }
        servicioPokemon.precargarDatos();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/load-status")
    public Map<String, Object> loadStatus() {
        Map<String, Object> estado = new HashMap<>();
        estado.put("loaded", servicioPokemon.getEstadoCarga().getProgresoActual());
        estado.put("total", servicioPokemon.getEstadoCarga().getTotal());
        estado.put("status", servicioPokemon.getEstadoCarga().isTerminado() ? "done" : "loading");
        return estado;
    }

}
