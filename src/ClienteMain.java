
import java.io.IOException;

public class ClienteMain {
    public static void main(String[] args) {
        try {
            BatalhaNaval jogo = new BatalhaNaval();
            Saida saida = new Saida();
            
            saida.setJogo(jogo);
            jogo.setSaida(saida);
            
            saida.renderizarTabuleiroJogador();
            saida.renderizarTabuleiroInimigo();
            saida.pedirPontoAtaque();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
