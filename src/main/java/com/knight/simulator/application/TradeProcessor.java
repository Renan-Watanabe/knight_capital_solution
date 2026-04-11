package com.knight.simulator.application;

import com.knight.simulator.domain.Account;
import com.knight.simulator.security.KillSwitch;
import com.knight.simulator.security.SecurityPolicy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeProcessor {
    private final Account account;
    private final List<SecurityPolicy> policies;
    private final KillSwitch killSwitch;

    public TradeProcessor (Account account, KillSwitch killSwitch, List<SecurityPolicy> policies) {
        this.account = account;
        this.killSwitch = killSwitch;
        this.policies = policies;
    }

    public void executeTrade (double lossValue) {
        // Se o sistema já parou, recusa qualquer tentativa na hora
        if (killSwitch.isTriggered()) {
            return;
        }

        // Retira o dinheiro da conta
        account.deduct(lossValue);

        // Testa todas as políticas de segurança
        for (SecurityPolicy policy : policies) {
            if (policy.isViolated(account.getBalance())) {
                killSwitch.trigger(policy.getAlertMessage());
                break; // Quebra o loop na primeira regra não cumprida
            }
        }
    }
}
