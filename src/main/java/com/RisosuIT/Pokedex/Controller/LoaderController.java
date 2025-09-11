package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.Service.ServicePokemon;
import com.RisosuIT.Pokedex.State.ApplicationLoadState;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoaderController {

    private final ServicePokemon servicePokemon;
    private final ApplicationLoadState loadState;

    public LoaderController(ServicePokemon servicePokemon, ApplicationLoadState loadState) {
        this.servicePokemon = servicePokemon;
        this.loadState = loadState;
    }

    @PostMapping("/start-load")
    public ResponseEntity<Void> startLoad() {
        CompletableFuture.runAsync(servicePokemon::preloadData);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/load-status")
    public Map<String, Object> getStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("loaded", loadState.getLoaded());
        map.put("total", loadState.getTotal());
        map.put("status", loadState.isDone() ? "done" : "building");
        return map;
    }
}
