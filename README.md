🛡️ Knight Capital Simulator - Resiliência em Sistemas HFT
Este projeto é uma Prova de Conceito desenvolvida para simular o cenário de falha sistêmica da Knight Capital (2012). O objetivo principal é demonstrar como a Arquiterua de software é de extrema importância em sistemas de negociação de alta frequência (HFT), utilizando Java e Spring Boot.

## Arquitetura do Sistema
A solução foi estruturada seguindo princípios de Clean Architecture e SOLID, garantindo que as regras de segurança sejam independentes da lógica de negócio.

## Camadas e Componentes:
domain: Contém a classe Account, que gerencia o saldo utilizando métodos sincronizados para garantir a consistência em ambientes multithreading.

security: Camada de proteção que utiliza o Strategy Pattern. Todas as defesas implementam a interface SecurityPolicy.

DropVelocityPolicy: Monitora a velocidade de perda financeira em uma janela de tempo (ex: R$ 200.000 em 100ms).

VolumeAnomalyPolicy: Implementa um Rate Limiting agressivo para detectar anomalias de volume (ex: limite de 50 ordens/segundo), utilizando programação Lock-Free com AtomicInteger para alta performance.

KillSwitch: Mecanismo de interrupção imediata que bloqueia o sistema ao detectar violações.

## application:

TradeProcessor: O maestro que executa ordens e valida todas as políticas de segurança antes de confirmar a transação.

ChaosSimulatorService: Gerencia as duas fases da simulação: o comportamento estocástico do mercado normal e o ataque de falha (Power Peg).

infrastructure: Responsável pela configuração do Spring (AppConfig) e exposição de endpoints REST (DashboardController) para o monitoramento em tempo real.

## Dashboard de Monitoramento
O sistema conta com uma interface web (index.html) que permite visualizar o saldo da corretora em tempo real através de um gráfico de linha dinâmico. Através deste painel, é possível observar o sistema "respirando" com o mercado normal e, posteriormente, acionar manualmente o glitch para testar as travas de segurança.

## Como Executar a Simulação
Pré-requisitos:
Java 17 ou superior instalado.

Maven Wrapper (incluído no repositório).

## Passo a Passo:
Clonar o repositório:

git clone https://github.com/SEU_USUARIO/knight_capital_solution.git
cd knight_capital_solution

## Executar o projeto:
No terminal, dentro da pasta raiz, execute:

./mvnw spring-boot:run
(No Windows: mvnw.cmd spring-boot:run)

## Acessar o Monitor:

Abra o seu navegador e acesse:
http://localhost:8080/index.html

## Simular o Erro:

Ao carregar, você verá o saldo de R$ 400.000.000,00 flutuando conforme as transações normais de mercado.

Clique no botão "ATIVAR GLITCH". O sistema iniciará o envio massivo de ordens erradas, e você verá o KillSwitch desarmar o sistema instantaneamente ao atingir os limites de segurança configurados
