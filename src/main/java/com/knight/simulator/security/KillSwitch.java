package com.knight.simulator.security;

import lombok.Getter;

public class KillSwitch {
    private boolean active = false;
    @Getter
    private String reason = "";

    public void trigger (String reason) {
        this.active = true;
        this.reason = reason;
        System.err.println("KILL SWITCH ATIVADO: " + reason);
    }

    public boolean isTriggered () {
        return this.active;
    }

}
