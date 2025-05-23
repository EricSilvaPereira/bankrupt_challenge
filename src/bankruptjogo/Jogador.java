/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankruptjogo;

/**
 *
 * @author erics
 */
import java.util.Random;

public class Jogador {

    private final String nome;
    private final Comportamento comportamento;
    private int saldo = 300;
    private int posicao = 0;
    private boolean ativo = true;
    private final Random aleatorio = new Random();

    public Jogador(String nome, Comportamento comportamento) {
        this.nome = nome;
        this.comportamento = comportamento;
    }

    public String getNome() {
        return nome;
    }

    public int getSaldo() {
        return saldo;
    }

    public Comportamento getComportamento() {
        return comportamento;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void jogarDado() {
        int passos = aleatorio.nextInt(6) + 1;
        if (posicao + passos >= Jogo.TAMANHO_TABULEIRO) {
            saldo += 100;
        }
        posicao = (posicao + passos) % Jogo.TAMANHO_TABULEIRO;
    }

    public void executarTurno(Propriedade[] tabuleiro) {
        if (!ativo) {
            return;
        }

        jogarDado();
        Propriedade propriedade = tabuleiro[posicao];

        if (propriedade.estaDisponivel()) {
            boolean comprar = false;
            switch (comportamento) {
                case IMPULSIVO:
                    comprar = true;
                    break;
                case EXIGENTE:
                    comprar = propriedade.getAluguel() > 50;
                    break;
                case CAUTELOSO:
                    comprar = saldo - propriedade.getPreco() >= 80;
                    break;
                case ALEATORIO:
                    comprar = aleatorio.nextBoolean();
                    break;
            }

            if (comprar && saldo >= propriedade.getPreco()) {
                saldo -= propriedade.getPreco();
                propriedade.setDono(this);
            }
        } else if (propriedade.getDono() != this) {
            pagarAluguel(propriedade);
        }

        if (saldo < 0) {
            ativo = false;
            for (Propriedade prop : tabuleiro) {
                if (prop.getDono() == this) {
                    prop.setDono(null);
                }
            }
        }
    }

    private void pagarAluguel(Propriedade propriedade) {
        Jogador dono = propriedade.getDono();
        saldo -= propriedade.getAluguel();
        if (dono != null) {
            dono.saldo += propriedade.getAluguel();
        }
    }
}
