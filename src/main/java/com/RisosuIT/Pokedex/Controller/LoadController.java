package com.RisosuIT.Pokedex.Controller;

// LoadController.java

import com.RisosuIT.Pokedex.Service.LoadingService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoadController {

    private final LoadingService loadingService;

    public LoadController(LoadingService loadingService) {
        this.loadingService = loadingService;
    }

    @GetMapping("/load-status")
    public Map<String, Object> status() {
        return loadingService.getStatus();
    }

    @PostMapping("/start-load")
    public ResponseEntity<?> startLoad() {
        // example: calcular lista de páginas a prefetch
        List<String> pages = IntStream.range(0, 100).mapToObj(i -> "page-" + i).collect(Collectors.toList());
        loadingService.startBackgroundLoad(pages);
        return ResponseEntity.accepted().build();
    }
}
