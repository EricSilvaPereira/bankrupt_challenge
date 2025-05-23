/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankruptjogo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author erics
 */


public class Jogo {
    public static final int TAMANHO_TABULEIRO = 20;
    private final Propriedade[] tabuleiro = new Propriedade[TAMANHO_TABULEIRO];
    private final List<Jogador> jogadores = new ArrayList<>();
    private int rodadas = 0;
    private boolean timeout = false;

    public Jogo() {
        carregarTabuleiro();
        jogadores.add(new Jogador("Impulsivo", Comportamento.IMPULSIVO));
        jogadores.add(new Jogador("Exigente", Comportamento.EXIGENTE));
        jogadores.add(new Jogador("Cauteloso", Comportamento.CAUTELOSO));
        jogadores.add(new Jogador("Aleatório", Comportamento.ALEATORIO));
        Collections.shuffle(jogadores);
    }

    private void carregarTabuleiro() {
        try (Scanner scanner = new Scanner(new File("gameConfig.txt"))) {
            for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
                int preco = scanner.nextInt();
                int aluguel = scanner.nextInt();
                tabuleiro[i] = new Propriedade(preco, aluguel);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo gameConfig.txt não encontrado.");
            System.exit(1);
        }
    }

    public Jogador jogar() {
        while (rodadas < 1000 && jogadores.stream().filter(Jogador::estaAtivo).count() > 1) {
            for (Jogador jogador : jogadores) {
                if (jogador.estaAtivo())
                    jogador.executarTurno(tabuleiro);
            }
            rodadas++;
        }

        if (rodadas >= 1000)
            timeout = true;

        return jogadores.stream()
                .filter(Jogador::estaAtivo)
                .max(Comparator.comparingInt(Jogador::getSaldo))
                .orElse(null);
    }

    public int getRodadas() {
        return rodadas;
    }

    public boolean foiTimeout() {
        return timeout;
    }

    public Comportamento getVencedorComportamento() {
        Jogador vencedor = jogar();
        return vencedor != null ? vencedor.getComportamento() : null;
    }
}