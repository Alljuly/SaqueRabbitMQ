# 🐰 SaqueRabbitMQ

Sistema de notificação de saques bancários utilizando **RabbitMQ** como message broker.

Quando um saque é realizado, um evento é publicado em uma fila e um consumidor processa a mensagem simulando o envio de um e-mail de notificação ao titular da conta.

---

## 🏗️ Arquitetura

```
┌──────────────┐       ┌─────────────┐       ┌───────────────┐
│   Publisher  │──────▶│  RabbitMQ   │──────▶│  Subscriber   │
│  (Saque)     │       │  (Fila)     │       │  (E-mail)     │
└──────────────┘       └─────────────┘       └───────────────┘
```

### Hierarquia de Classes

```
«interface» Conta
       │
«abstract» ContaBancaria
       ├── ContaCorrente
       └── ContaPoupanca

«interface» EventPublisher
       └── RabbitMQPublisher

«interface» EventSubscriber
       └── RabbitMQSubscriber
```

---

## 📁 Estrutura do Projeto

```
src/main/java/br/edu/ifal/
├── model/
│   ├── Conta.java                 # Interface de conta bancária
│   ├── ContaBancaria.java         # Classe abstrata com lógica compartilhada
│   ├── ContaCorrente.java         # Conta corrente (sem cheque especial)
│   └── ContaPoupanca.java         # Conta poupança (com rendimento)
├── messaging/
│   ├── EventPublisher.java        # Interface de publicação de eventos
│   ├── EventSubscriber.java       # Interface de consumo de eventos
│   ├── RabbitMQPublisher.java     # Implementação RabbitMQ (publisher)
│   └── RabbitMQSubscriber.java    # Implementação RabbitMQ (subscriber)
├── SaquePublisher.java            # Realiza saque e publica evento
└── SaqueSubscriber.java           # Consome evento e simula envio de e-mail
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Java       | 21     |
| RabbitMQ Client | 5.20.0 |
| Gson       | 2.10.1 |
| SLF4J      | 2.0.12 |
| Maven      | 3.x    |

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21+
- Maven
- RabbitMQ rodando em `localhost:5672`

#### Opção 1: Docker

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

#### Opção 2: Homebrew (macOS)

```bash
brew install rabbitmq
brew services start rabbitmq
```

#### Opção 3: Instalação nativa (Linux)

```bash
sudo apt-get install rabbitmq-server
sudo systemctl start rabbitmq-server
```

> Painel de gerenciamento disponível em http://localhost:15672 (guest/guest)

### Compilar

```bash
mvn compile
```

### Executar o Subscriber (Terminal 1)

```bash
mvn exec:java -Dexec.mainClass="br.edu.ifal.SaqueSubscriber"
```

### Executar o Publisher (Terminal 2)

```bash
mvn exec:java -Dexec.mainClass="br.edu.ifal.SaquePublisher"
```

---

## 📨 Exemplo de Saída

**Publisher:**
```
=== OPERAÇÃO DE SAQUE ===
Titular: João Silva
Saldo atual: R$ 1000.0
Valor do saque: R$ 250.0
Saque realizado com sucesso! Novo saldo: R$ 750.0
Evento de saque publicado na fila 'send-emails'
```

**Subscriber:**
```
========== SIMULAÇÃO DE E-MAIL ==========
Para: joao@email.com
Assunto: Notificação de Saque
-----------------------------------------
Olá, João Silva!
Um saque de R$ 250.0 foi realizado em sua conta.
Saldo restante: R$ 750.0
Data/Hora: 2026-05-09T15:47:01.123
=========================================
```

---

## 📐 Princípios Aplicados

- **Programação para interfaces** — desacoplamento entre camadas
- **Herança** — hierarquia `Conta → ContaBancaria → ContaCorrente/ContaPoupanca`
- **Polimorfismo** — diferentes tipos de conta com comportamentos próprios
- **Responsabilidade única** — cada classe tem um propósito claro
- **Inversão de dependência** — código depende de abstrações, não de implementações concretas

---

## 📝 Licença

Projeto acadêmico — IFAL.
