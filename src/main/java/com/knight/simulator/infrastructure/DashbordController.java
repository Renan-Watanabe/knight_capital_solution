package com.knight.simulator.infrastructure;

import com.knight.simulator.application.ChaosSimulatorService;
import com.knight.simulator.domain.Account;
import com.knight.simulator.security.KillSwitch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashbordController {

    private final ChaosSimulatorService simulatorService;
    private final Account account;
    private final KillSwitch killSwitch;

    public DashbordController(ChaosSimulatorService simulatorService,  Account account, KillSwitch killSwitch) {
        this.simulatorService = simulatorService;
        this.account = account;
        this.killSwitch = killSwitch;
    }

    @PostMapping("/glitch")
    public ResponseEntity<String> ativarGlitch() {
        simulatorService.startGlitch();
        return ResponseEntity.ok("Glitch ativado");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("saldo", account.getBalance());
        status.put("killSwitchAtivado", killSwitch.isTriggered());
        return ResponseEntity.ok(status);
    }
}
