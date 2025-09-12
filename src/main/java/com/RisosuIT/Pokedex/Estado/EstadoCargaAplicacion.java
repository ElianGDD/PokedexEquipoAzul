package com.RisosuIT.Pokedex.Estado;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class EstadoCargaAplicacion {

    private final AtomicInteger progresoActual = new AtomicInteger();
    private volatile int total = 0;
    private volatile boolean terminado = false;
    private volatile boolean enCurso = false;

    public void reiniciar(int total) {
        this.total = total;
        this.progresoActual.set(0);
        this.terminado = false;
    }

    public void incrementar() {
        if (progresoActual.get() < total) {
            progresoActual.incrementAndGet();
        }
    }

    public void marcarTerminado() {
        this.terminado = true;
        this.enCurso = false;
    }

    public int getProgresoActual() {
        return progresoActual.get();
    }

    public int getTotal() {
        return total;
    }

    public boolean isTerminado() {
        if (total <= 0) {
            return false;
        }
        return terminado || progresoActual.get() >= total;
    }

    public double getPorcentajeAvance() {
        if (total == 0) {
            return 0;
        }
        return (progresoActual.get() * 100.0) / total;
    }

    public boolean isEnCurso() {
        return enCurso && !terminado;
    }
}
