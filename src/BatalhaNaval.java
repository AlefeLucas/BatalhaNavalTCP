
import java.io.IOException;

public class BatalhaNaval {

    public static final int TAMANHO_TABULEIRO = 10;
    private Tabuleiro tabuleiroJogador;
    private final ClienteBatalhaNaval socketCliente;
    private Saida saida;

    public BatalhaNaval(String ip, int porta) throws IOException {
        System.out.println("Conectando ao servidor " + ip + " na porta " + porta);
        socketCliente = new ClienteBatalhaNaval(ip, porta);
        socketCliente.setJogo(this);

        criaNovoTabuleiroJogador();
    }

    private void criaNovoTabuleiroJogador() {
        tabuleiroJogador = new Tabuleiro(TAMANHO_TABULEIRO, TAMANHO_TABULEIRO);
        tabuleiroJogador.addNavios();

        socketCliente.setTabuleiro(tabuleiroJogador);
    }

    Celula serAtacado(Ponto ponto) throws IOException {
        Celula celula = tabuleiroJogador.getCelula(ponto.getX(), ponto.getY());
        celula.setAtacado(true);

        saida.imprime();
        boolean minhaVez = false;
        if (celula.isNavio()) {
            Navio navio = celula.getNavio();
            navio.subtraiTamanhoRestante();
            //Inimigo acertou e jogara dnv

        } else {
            //Inimigo errou, sua vez
            minhaVez = true;
        }

        if (verificaFimJogo()) {
            enviaVitoria();
            perdeu();
            System.exit(0);
        } else if (minhaVez) {
            saida.pedirPontoAtaque();

        }

        return celula;
    }

    void vitoria() {
        System.out.println("Você Venceu!");
    }

    void atualizaCelulaInimiga(Celula celula) throws IOException {
        /**
         * TODO So faz sentido esse método com GUI Imprimir tabuleiros?
         */
        Celula celula1 = saida.getTabuleiroInimigo().getCelula(celula.getX(), celula.getY());
        celula1.setAtacado(true);
        celula1.setNavio(celula.getNavio());

        saida.imprime();

        if (celula.isNavio()) {
            saida.pedirPontoAtaque();
        }
    }

    private boolean verificaFimJogo() {
        return tabuleiroJogador.getNumeroCelulasMortasNavios() == Tabuleiro.getNumeroCelulasNavios();
    }

    private void perdeu() {
        System.out.println("Você Perdeu!");
    }

    private void enviaVitoria() throws IOException {
        socketCliente.enviaVitoria();
    }

    public Tabuleiro getTabuleiroJogador() {
        return tabuleiroJogador;
    }

    void setSaida(Saida saida) throws IOException {
        this.saida = saida;

    }

    public void atacar(int x, int y) throws IOException {
        Ponto ponto = new Ponto(x, y);

        socketCliente.atacar(ponto);
    }

    void notificarIniciou(boolean vez) throws IOException {
        if (saida != null) {
            saida.notificarIniciou(vez);
        } 
    }

}
