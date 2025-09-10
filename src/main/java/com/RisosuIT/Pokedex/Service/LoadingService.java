package com.RisosuIT.Pokedex.Service;

// LoadingService.java
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

@Service
public class LoadingService {

    private final AtomicInteger loaded = new AtomicInteger(0);
    private final AtomicInteger total = new AtomicInteger(0);
    private final AtomicReference<String> status = new AtomicReference<>("idle");

    public void startBackgroundLoad(List<String> pagesToFetch) {
        total.set(pagesToFetch.size());
        loaded.set(0);
        status.set("building");
        CompletableFuture.runAsync(() -> {
            for (String p : pagesToFetch) {
                try {
                    // tu lógica de fetch/cache (p. ej. WebClient call)
                    // simulate:
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                loaded.incrementAndGet();
            }
            status.set("done");
        });
    }

    public Map<String, Object> getStatus() {
        return Map.of(
                "loaded", loaded.get(),
                "total", total.get(),
                "status", status.get()
        );
    }

    // llamado por tu init o endpoint para lanzar la precarga
    public void setTotals(int tot) {
        total.set(tot);
    }
}
