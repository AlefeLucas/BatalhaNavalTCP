
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JogoServidor {

    public static final int TAMANHO_TABULEIRO = 10;
    private Tabuleiro tabuleiroJogador;
    private final Servidor socketServidor;
    private SaidaServidor saida;
    private final ArrayList<Ponto> alvosDisponiveis;

    public JogoServidor(int porta) throws IOException, InterruptedException {
        System.out.println("Iniciando servidor na porta " + porta);
        
        this.alvosDisponiveis = getAlvosDisponiveis();
        
        socketServidor = new Servidor(porta);
        socketServidor.setJogo(this);

        criaNovoTabuleiroJogador();
    }

    private static ArrayList<Ponto> getAlvosDisponiveis() {
        ArrayList<Ponto> alvosDisponiveis      = new ArrayList<>(100);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                alvosDisponiveis.add(new Ponto(i, j));
                
            }
        }
        return alvosDisponiveis;
    }

    private void criaNovoTabuleiroJogador() {
        tabuleiroJogador = new Tabuleiro(TAMANHO_TABULEIRO, TAMANHO_TABULEIRO);
        tabuleiroJogador.addNavios();

        socketServidor.setTabuleiro(tabuleiroJogador);
    }

    Celula serAtacado(Ponto ponto) throws IOException, InterruptedException {
        Celula celula = tabuleiroJogador.getCelula(ponto.getX(), ponto.getY());
        celula.setAtacado(true);

        saida.serAtacado(ponto);

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

    void atualizaCelulaInimiga(Celula celula) throws IOException, InterruptedException {
        Celula celula1 = saida.getTabuleiroInimigo().getCelula(celula.getX(), celula.getY());
        celula1.setAtacado(true);
        celula1.setNavio(celula.getNavio());

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
        socketServidor.enviaVitoria();
    }

    public Tabuleiro getTabuleiroJogador() {
        return tabuleiroJogador;
    }

    void setSaida(SaidaServidor saida) throws IOException {
        this.saida = saida;

    }

    void notificarIniciou(boolean vez) throws IOException, InterruptedException {
        if (saida != null) {
            saida.notificarIniciou(vez);
        } else {
            System.out.println("SAIDA NULA");
        }
    }

    public void iniciar() throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        socketServidor.notificaIniciar();
    }

    void atacar() throws IOException {
        Random random = new Random();
        Ponto alvo = alvosDisponiveis.remove(random.nextInt(alvosDisponiveis.size()));
        
        System.out.println("Atacando: \"" + ((char) (alvo.getX() + 'A')) + "" + alvo.getY() + "\"");
        socketServidor.atacar(alvo);
    }

}
