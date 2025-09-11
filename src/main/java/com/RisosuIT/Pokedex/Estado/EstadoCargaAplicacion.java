package com.RisosuIT.Pokedex.Estado;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class EstadoCargaAplicacion {

    private final AtomicInteger progresoActual = new AtomicInteger();
    private volatile int total = 0;
    private volatile boolean terminado = false;

    public void reiniciar(int total) {
        this.total = total;
        this.progresoActual.set(0);
        this.terminado = false;
    }

    public void incrementar() {
        progresoActual.incrementAndGet();
    }

    public void marcarTerminado() {
        this.terminado = true;
    }

    public int getProgresoActual() {
        return progresoActual.get();
    }

    public int getTotal() {
        return total;
    }

    public boolean isTerminado() {
        // Puede marcarse manualmente o deducirse si ya procesamos todo
        return terminado || progresoActual.get() >= total;
    }

    public double getPorcentajeAvance() {
        if (total == 0) {
            return 0;
        }
        return (progresoActual.get() * 100.0) / total;
    }
}
