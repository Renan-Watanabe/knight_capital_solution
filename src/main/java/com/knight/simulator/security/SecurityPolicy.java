package com.knight.simulator.security;

public interface SecurityPolicy {
    // Retorna true se violada indicando que o sistema deve parar
    boolean isViolated(double currentBalance);

    // Mensagem de alerta que irá aparecer no log
    String getAlertMessage();
}
