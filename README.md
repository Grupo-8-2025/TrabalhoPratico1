# Remake Dots and Boxes

Jogo desenvolvido em Java inspirado no clássico jogo Dots and Boxes, com o objetivo de reproduzir suas principais mecânicas de jogabilidade. Este projeto foi desenvolvido como trabalho prático da disciplina Linguagem e Técnicas de Programação 2 no curso técnico de informática do CEFET-MG, utilizando a biblioteca LibGDX para recursos gráficos e multimídia.

---

## Funcionalidades:
- Jogo por turnos entre dois jogadores
- Desenho de linhas horizontais e verticais na grade
- Sistema de detecção de caixas completas
- Marcação automática de pontos ao completar uma caixa
- Exibição da pontuação do jogador e da máquina
- Vitória ou derrota ao final da partida

## Tecnologias usadas:
- Java (JDK 8 ou superior)
- Gradle
- LibGDX
- VS Code

---

## Como executar

### Pelo Terminal
1. Baixe e extraia o arquivo zip do projeto
2. Copie a pasta principal do projeto para o diretório desejado
3. Abra o terminal na raiz onde está o projeto
4. Compile e execute utilizando o comando:
```bash
gradlew lwjgl3:run
```

### Pela sua IDE
1. Baixe e extraia o arquivo zip do projeto
2. Copie a pasta principal do projeto para o diretório desejado
3. Abra o projeto na sua IDE de preferência
4. Navegue até o arquivo:
```
lwjgl3/src/main/java/com/tp2/megamanx/lwjgl3/Lwjgl3Launcher.java
```
5. Clique com o botão direito no arquivo e selecione Run ou Executar

### Observações importantes:
- Execute o jogo somente em modo janela
- Utilize a resolução recomendada de 800x500 pixels
- Não utilize o modo tela cheia, pois pode ocorrer erro de escala gráfica

---

## Como Jogar?

### Objetivo
Completar o maior número possível de caixas antes do fim da partida

### Controles
- **🖱 Clique do mouse**: Conectar pontos adjacentes com linhas horizontais ou verticais em uma grade
