
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Servidor {

    private final ServerSocket serverSocket;
    private ConnectionCliente cliente;
    private Tabuleiro tabuleiro;
    private JogoServidor jogo;

    public Servidor(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        conectar();
      
    }

    public void setJogo(JogoServidor jogo) {
        this.jogo = jogo;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void enviaVitoria() throws IOException {
        getConnection().write("WIN");
    }

    public void atacar(Ponto ponto) throws IOException {
        getConnection().write(ponto);
    }

    public void notificaIniciar() throws IOException, InterruptedException {
        Random r = new Random();
        boolean vez;
    
        vez = r.nextBoolean();
        cliente.write(vez);
        this.notificarIniciou(!vez);
        
        
    }

    public void notificarIniciou(boolean vez) throws IOException, InterruptedException {

        jogo.notificarIniciou(vez);
    }

    public void atualizaCelulaInimiga(Celula celula) throws IOException, InterruptedException {
        jogo.atualizaCelulaInimiga(celula);
    }

    public void setVitoria() {
        jogo.vitoria();
    }

    public Celula serAtacado(Ponto ponto) throws IOException, InterruptedException {
        return jogo.serAtacado(ponto);
    }

    public ConnectionCliente getConnection() {
        return this.cliente;
    }

    private void conectar() throws IOException {

        System.out.println("Aguardando cliente... ");
        Socket socket = serverSocket.accept();

        System.out.println("Obteve cliente.");

        cliente = new ConnectionCliente(socket, this);

    }
}
