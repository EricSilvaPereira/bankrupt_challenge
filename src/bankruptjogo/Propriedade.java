/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankruptjogo;

/**
 *
 * @author erics
 */
public class Propriedade {
    private final int preco;
    private final int aluguel;
    private Jogador dono;

    public Propriedade(int preco, int aluguel) {
        this.preco = preco;
        this.aluguel = aluguel;
        this.dono = null;
    }

    public int getPreco() {
        return preco;
    }

    public int getAluguel() {
        return aluguel;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    public boolean estaDisponivel() {
        return dono == null;
    }
}
