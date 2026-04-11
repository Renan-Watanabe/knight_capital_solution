package com.knight.simulator.security;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VolumeAnomalyPolicy implements SecurityPolicy {
    private final int maxOrdersPerSecond;

    // Atomic para opreações rápidas
    private final AtomicInteger ordersInCurrentSecond;
    private final AtomicLong secondTimer;

    public VolumeAnomalyPolicy(int maxOrdersPerSecond) {
        this.maxOrdersPerSecond = maxOrdersPerSecond;
        this.ordersInCurrentSecond = new AtomicInteger(0);
        this.secondTimer = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean isViolated(double currentBalance) {
        long now = System.currentTimeMillis();
        long timer = secondTimer.get();

        // Depois de um segundo, zera o contador
        if (now - timer >= 1000) {
            //Tenta atualizar o timer, caso consiga, zera as ordens.
            if (secondTimer.compareAndSet(timer, now)) {
                ordersInCurrentSecond.set(0);
            }
        }

        // Adiciona 1 na contagem e pega o valor atualizado na mesma fração de segundo
        int currentOrders = ordersInCurrentSecond.incrementAndGet();

        // Trava apenas se passar do limite
        return currentOrders > maxOrdersPerSecond;
    }

    @Override
    public String getAlertMessage() {
        return "CRÍTICO: Anomalia de Volume!";
    }
}
