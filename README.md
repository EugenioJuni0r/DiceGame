# DiceGame (jogo dos dados)
## Levantamento de Requisitos:
   Requistos Não Funcionais:
   
   - O jogo deve calcular e exibir o resultado em menos de 3 segundo.
   - Interface intuitiva e responsiva, acessível em desktop.
   - Mensagens claras e visuais para erros e resultados (ex: “Aposta inválida” ou “Máquina venceu”).
   - O sistema deve validar todas as entradas (nomes e apostas) antes do envio ao servidor.

   Requistos Funcionais:
   
   - Cada jogador deve escolher o valor para sua aposta;
   - Lançar os dados;
   - Apresentar os Resultados;
   - Informar se alguém ganhou, ou a casa se for o caso, e apresentar o nome dos vencedores.

   Regras de Negócios:
    
   - Máximo de 25 jogadores por casa;
   - O valor escolhido deve estar entre 2 e 12 (Resultados possíveis);
   - Pode ter mais de um vencedor por aposta.

## Diagrama de Classes
![Diagrama de Classes](´/diagrama_classes.png´)

## Diagrama de Casos de Uso
![Diagrama de Casos de Uso](´/diagrama_casos_usos.png´)
