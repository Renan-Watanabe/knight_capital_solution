package com.knight.simulator.application;

import com.knight.simulator.domain.Account;
import com.knight.simulator.security.KillSwitch;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@Service
public class ChaosSimulatorService {
    private final TradeProcessor tradeProcessor;
    private final KillSwitch killSwitch;
    private final Account account;
    private final double openingBalance;
    private volatile boolean glitchAtivado = false;
    private volatile boolean mercadoAberto = false;

    public ChaosSimulatorService(TradeProcessor tradeProcessor, KillSwitch killSwitch, Account account) {
        this.tradeProcessor = tradeProcessor;
        this.killSwitch = killSwitch;
        this.account = account;
        this.openingBalance = account.getBalance();
    }

    public void initializeMarket() {
    this.mercadoAberto = true;
    System.out.println("SISTEMA ONLINE: Mercado Rodando");

    new Thread(() -> {
        Random random = new Random();

        // Roda normal até que o glitch aconteça
        while (mercadoAberto && !glitchAtivado && !killSwitch.isTriggered()) {
            double fluctuation = 100 + (2000 - 100) * random.nextDouble();
            if (random.nextBoolean()) account.addFunds(fluctuation);
            else tradeProcessor.executeTrade(fluctuation);

            System.out.printf("[Mercado Normal] Saldo: R$%.2f%n", account.getBalance());

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }

            if (glitchAtivado && !killSwitch.isTriggered()) {
                iniciarAtaquePowerPeg();
            }
        }).start();
    }

    public void startGlitch() {
        this.glitchAtivado = true;
    }

    private void iniciarAtaquePowerPeg() {
        System.out.println("SISTEMA ONLIINE: Ataque Peg");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            executor.submit(() -> {
                if (!killSwitch.isTriggered()) {
                    tradeProcessor.executeTrade(5000);
                    System.out.printf("[GLITCH] Saldo: R$%.2f%n", account.getBalance());
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception e) {}

        System.out.println("SIMULAÇÃO ENCERRADA");
        System.out.println("Kill switch " + (killSwitch.isTriggered() ? "ATIVADO" : "DESATIVADO"));
        System.out.printf("Saldo Final: R$%.2f%n", account.getBalance());
    }
}


