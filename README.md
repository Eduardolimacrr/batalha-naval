# Batalha Naval em Java

Este é um jogo de Batalha Naval em Java, com suporte a partidas multiplayer via rede (servidor/cliente), ranking de pontuação e salvamento de estado da partida.

## Estrutura do Projeto

- `src/`: Código-fonte Java.
- `bin/`: Arquivos compilados.
- `lib/`: Dependências externas (se houver).
- `ranking.txt`: Arquivo de ranking dos jogadores.
- `.vscode/`: Configurações do projeto para o VS Code.

## Como Compilar

Abra o terminal na pasta `BatalhaNaval` e execute:

```sh
javac -d bin src/**/*.java
```

## Como Executar

No terminal, execute:

```sh
java -cp bin Main
```

## Como Jogar

1. **Servidor:**  
   Em um computador, escolha a opção `[s] Servidor` e informe seu nome.
2. **Cliente:**  
   Em outro computador, escolha `[c] Cliente`, informe o IP do servidor e seu nome.
3. O jogo segue em turnos, onde cada jogador tenta acertar as embarcações do adversário.
4. Para atacar, digite as coordenadas no formato `linha coluna` (exemplo: `5 7`).
5. Para sair, digite `sair` durante sua vez.
6. Ao final, a pontuação é registrada no ranking.

## Visualizando o Ranking

No menu principal, escolha `[r] Ver ranking` para exibir a lista dos melhores jogadores e suas pontuações.

## Salvamento de Partida

O jogo salva automaticamente o progresso. Se o jogo for interrompido, ao iniciar novamente, será possível continuar de onde parou.

## Requisitos

- Java 11 ou superior.
- Dois computadores na mesma rede para jogar multiplayer.

## Créditos

Desenvolvido para fins didáticos.

---

Qualquer dúvida, consulte o código-fonte ou abra uma issue!
