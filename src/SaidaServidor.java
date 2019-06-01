
import java.io.IOException;
import java.util.concurrent.TimeUnit;


class SaidaServidor {

    private JogoServidor jogo;
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroInimigo;

    public SaidaServidor() {
        System.out.println("Jogo Batalha Naval TCP com um servidor e dois clientes.");
      
    }

    void setJogo(JogoServidor jogo) {
        this.jogo = jogo;
    }

    void renderizarTabuleiroJogador() {
        this.tabuleiroJogador = jogo.getTabuleiroJogador();

    }

    void renderizarTabuleiroInimigo() {
        this.tabuleiroInimigo = new Tabuleiro(10, 10);

    }

    void imprime() {
        System.out.println("\nTABULEIRO DO SERVIDOR:");
        System.out.println(this.tabuleiroJogador);
        System.out.println("");
    }

    void pedirPontoAtaque() throws IOException, InterruptedException {
        
            Thread threadPedirAtaque = new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    jogo.atacar();
                } catch (IOException |InterruptedException ex) {
                   throw new RuntimeException(ex);
                }
            });
            
            threadPedirAtaque.start();
       
    }

   

    public Tabuleiro getTabuleiroInimigo() {
        return tabuleiroInimigo;
    }

    void notificarIniciou(boolean vez) throws IOException, InterruptedException {
        System.out.println("Adversário encontrado.\nPartida iniciada.\n");
        if (vez) {
            this.pedirPontoAtaque();
        } else {
            System.out.println("O adversário inicia...\n");
        }
    }

    void aguardando() {
        System.out.println("Aguardando outro jogador.");
    }

    void serAtacado(Ponto ponto) {
        System.out.println("Ataque sofrido em \"" + (char) (ponto.getX() + 'A') + "" + ponto.getY() + "\"\n");
    }

    void feedbackAtaque(boolean isNavio) {
        System.out.println((isNavio ? "Acertou navio!" : "Acertou água.") + "\n");
    }

}
