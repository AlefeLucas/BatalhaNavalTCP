
import java.io.IOException;
import java.util.Scanner;


class Saida {

    private BatalhaNaval jogo;
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroInimigo;

    public Saida() {
        System.out.println("Jogo Batalha Naval TCP com um servidor e dois clientes.");
    }

    void setJogo(BatalhaNaval jogo) {
        this.jogo = jogo;
    }

    void renderizarTabuleiroJogador() {
        this.tabuleiroJogador = jogo.getTabuleiroJogador();
        System.out.println("SEU TABULEIRO:");
        System.out.println(this.tabuleiroJogador);
    }

    void renderizarTabuleiroInimigo() {
        this.tabuleiroInimigo = new Tabuleiro(10, 10);
        System.out.println("TABULEIRO INIMIGO:");
        System.out.println(this.tabuleiroInimigo);        
    }

    void imprime() {
        System.out.println("SEU TABULEIRO:");
        System.out.println(this.tabuleiroJogador);
        System.out.println("");
        System.out.println("TABULEIRO INIMIGO:");
        System.out.println(this.tabuleiroInimigo);  
        System.out.println("");
    }

    void pedirPontoAtaque() throws IOException {
        System.out.println("Digite coordenada para atacar (ex: A1): ");
        Scanner scanner = new Scanner(System.in);
        String linha = scanner.nextLine();
        int x = linha.charAt(0) - 'A';
        int y = linha.charAt(1) - '0';
                
        jogo.atacar(x, y);
    }

    public Tabuleiro getTabuleiroInimigo() {
        return tabuleiroInimigo;
    }
    
    

}
