
import java.io.IOException;

class ClienteBatalhaNaval extends Cliente {

    private Tabuleiro tabuleiro;
    private BatalhaNaval jogo;

    public ClienteBatalhaNaval(String ip, int port) throws IOException {
        super(ip, port);
        conectar();
    }

    void atualizaCelulaInimiga(Celula celula) {
        jogo.atualizaCelulaInimiga(celula);
    }

    public void setJogo(BatalhaNaval jogo) {
        this.jogo = jogo;
    }

    Celula serAtacado(Ponto ponto) throws IOException {
        return jogo.serAtacado(ponto);
    }
    
    public void atacar(Ponto ponto) throws IOException {
        getConnection().write(ponto);
    }

    void setVitoria() {
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

}
