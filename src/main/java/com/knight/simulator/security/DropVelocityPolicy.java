package com.knight.simulator.security;

public class DropVelocityPolicy implements SecurityPolicy {
    private final double maxLossAllowed;
    private final long timeWindowMs;

    private double lastBalance;
    private long lastCheckTime;

    public DropVelocityPolicy(double maxLossAllowed, long timeWindowMs) {
        this.maxLossAllowed = maxLossAllowed;
        this.timeWindowMs = timeWindowMs;
        this.lastCheckTime = System.currentTimeMillis();
        this.lastBalance = -1; // indica que ainda não inicou
    }

    @Override
    public boolean isViolated(double currentBalance) {
        if (this.lastBalance == -1) {
            this.lastBalance = currentBalance;
        }

        double moneyLost = this.lastBalance - currentBalance;
        long now = System.currentTimeMillis();

        if (moneyLost > maxLossAllowed) {
            return true;
        }

        if (now - lastCheckTime >= timeWindowMs) {
            this.lastBalance = currentBalance;
            this.lastCheckTime = now;
        }
        return false;
    }

    @Override
    public String getAlertMessage() {
        return "CRÍTICO: Velocidade de queda absruda detectada!";
    }
}
