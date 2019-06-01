
import java.io.IOException;

public class ServidorMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            JogoServidor jogo = new JogoServidor(Integer.parseInt(args[0]));
            SaidaServidor saida = new SaidaServidor();
            saida.aguardando();
            saida.setJogo(jogo);
            jogo.setSaida(saida);

            saida.renderizarTabuleiroJogador();
            saida.renderizarTabuleiroInimigo();
            saida.imprime();

            jogo.iniciar();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        } 

    }
}
