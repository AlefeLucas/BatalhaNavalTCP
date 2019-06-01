
import java.io.IOException;

public class ClienteMain {
    public static void main(String[] args) {
        try {
            BatalhaNaval jogo = new BatalhaNaval(args[0], Integer.parseInt(args[1]));
            Saida saida = new Saida();
            saida.aguardando();
            saida.setJogo(jogo);
            jogo.setSaida(saida);
            
            saida.renderizarTabuleiroJogador();
            saida.renderizarTabuleiroInimigo();
            saida.imprime();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
