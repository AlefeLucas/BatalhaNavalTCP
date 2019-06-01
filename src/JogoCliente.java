
import java.io.IOException;

public class JogoCliente {

    private Tabuleiro tabuleiroJogador;
    private final Cliente socketCliente;
    private SaidaCliente saida;

    public JogoCliente(String ip, int porta) throws IOException {
        System.out.println("Conectando ao servidor " + ip + " na porta " + porta);
        socketCliente = new Cliente(ip, porta);
        socketCliente.setJogo(this);

        criaNovoTabuleiroJogador();
    }

    private void criaNovoTabuleiroJogador() {
        tabuleiroJogador = new Tabuleiro(10, 10);
        tabuleiroJogador.addNaviosDeArquivo();

        socketCliente.setTabuleiro(tabuleiroJogador);
    }

    public Celula serAtacado(Ponto ponto) throws IOException {
        Celula celula = tabuleiroJogador.getCelula(ponto.getX(), ponto.getY());
        celula.setAtacado(true);

        saida.serAtacado(ponto);
        //saida.imprime();
        boolean minhaVez = false;
        if (!celula.isNavio()) {
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

    public void vitoria() {
        System.out.println("Você Venceu!");
    }

    public void atualizaCelulaInimiga(Celula celula) throws IOException {
        Celula celula1 = saida.getTabuleiroInimigo().getCelula(celula.getX(), celula.getY());
        celula1.setAtacado(true);
        celula1.setNavio(celula.getNavio());

        //saida.imprime();
        saida.feedbackAtaque(celula.isNavio());
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

    public void setSaida(SaidaCliente saida) throws IOException {
        this.saida = saida;

    }

    public void atacar(int x, int y) throws IOException {
        Ponto ponto = new Ponto(x, y);

        socketCliente.atacar(ponto);
    }

    public void notificarIniciou(boolean vez) throws IOException {
        if (saida != null) {
            saida.notificarIniciou(vez);
        } 
    }
    
    public static void main(String[] args) {
        try {
            JogoCliente jogo = new JogoCliente(args[0], Integer.parseInt(args[1]));
            SaidaCliente saida = new SaidaCliente();
            saida.aguardando();
            saida.setJogo(jogo);
            jogo.setSaida(saida);
            
            saida.renderizarTabuleiroJogador();
            saida.renderizarTabuleiroInimigo();
            saida.imprime();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        } 
    }

}
