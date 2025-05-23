/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankruptjogo;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author erics
 */

public class Simulador {
    public static void main(String[] args) {
        int rodadasTotais = 0;
        int timeouts = 0;
        Map<Comportamento, Integer> vitorias = new HashMap<>();

        for (Comportamento c : Comportamento.values()) {
            vitorias.put(c, 0);
        }

        for (int i = 0; i < 300; i++) {
            Jogo jogo = new Jogo();
            Comportamento vencedor = jogo.getVencedorComportamento();
            rodadasTotais += jogo.getRodadas();
            if (jogo.foiTimeout()) {
                timeouts++;
            }
            if (vencedor != null) {
                vitorias.put(vencedor, vitorias.get(vencedor) + 1);
            }
        }

        System.out.println("Resultados apos 300 partidas:");
        System.out.println("Partidas finalizadas por tempo limite: " + timeouts);
        System.out.println("Media de rodadas por partida: " + (rodadasTotais / 300));
        System.out.println("Vitorias por comportamento:");
        for (Map.Entry<Comportamento, Integer> entry : vitorias.entrySet()) {
            System.out.printf("%s: %d (%.2f%%)\n", entry.getKey(), entry.getValue(),
                    entry.getValue() * 100.0 / 300);
        }

        Comportamento melhor = vitorias.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
        System.out.println("Comportamento com mais vitorias: " + melhor);
    }
}