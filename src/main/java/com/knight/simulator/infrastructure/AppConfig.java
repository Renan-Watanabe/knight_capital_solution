package com.knight.simulator.infrastructure;

import com.knight.simulator.domain.Account;
import com.knight.simulator.security.DropVelocityPolicy;
import com.knight.simulator.security.KillSwitch;
import com.knight.simulator.security.SecurityPolicy;
import com.knight.simulator.security.VolumeAnomalyPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public Account account() {
        // Inicia com 400 milhões
        return new Account(400000000.00);
    }

    @Bean
    public KillSwitch killSwitch() {
        return new KillSwitch();
    }

    @Bean
    public List<SecurityPolicy> securityPolicies() {
        // Trava caso ocorra uma perda de R$200.000 em uma janela de 100 milissegundos
        return Arrays.asList(
                new DropVelocityPolicy(200000.00, 100),
                new VolumeAnomalyPolicy(50)
        );
    }
}
