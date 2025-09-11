package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import com.RisosuIT.Pokedex.Estado.EstadoCargaAplicacion;
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
        servicioPokemon.precargarDatos();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/load-status")
    public Map<String, Object> estado() {
        return Map.of(
                "loaded", estadoCarga.getProgresoActual(),
                "total", estadoCarga.getTotal(),
                "status", estadoCarga.isTerminado() ? "done" : "building"
        );
    }
}
