
import java.io.IOException;
import java.net.Socket;

class Cliente  {

    private ConnectionServidor servidor;
    private final Socket socket;
    private Tabuleiro tabuleiro;
    private JogoCliente jogo;

    public Cliente(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        conectar();
    }

    public ConnectionServidor getConnection(){
        return this.servidor;
    }
    
    public void atualizaCelulaInimiga(Celula celula) throws IOException {
        jogo.atualizaCelulaInimiga(celula);
    }

    public void setJogo(JogoCliente jogo) {
        this.jogo = jogo;
    }

    public Celula serAtacado(Ponto ponto) throws IOException {
        return jogo.serAtacado(ponto);
    }
    
    public void atacar(Ponto ponto) throws IOException {
        getConnection().write(ponto);
    }

    public void setVitoria() {
        jogo.vitoria();
    }

    private void conectar() throws IOException {
        servidor = new ConnectionServidor(socket, this);
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    
    public void enviaVitoria() throws IOException {
        getConnection().write("WIN");
    }

    public void notificarIniciou(boolean vez) throws IOException {
       
        jogo.notificarIniciou(vez);
    }

}
