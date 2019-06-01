
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JogoServidor {


    private Tabuleiro tabuleiroJogador;
    private final Servidor socketServidor;
    private SaidaServidor saida;
    private final ArrayList<Ponto> alvosDisponiveis;
    private Celula ultimaCelulaAtacada;

    public JogoServidor(int porta) throws IOException, InterruptedException {
        System.out.println("Iniciando servidor na porta " + porta);

        this.alvosDisponiveis = getAlvosDisponiveis();

        socketServidor = new Servidor(porta);
        socketServidor.setJogo(this);

        criaNovoTabuleiroJogador();
    }

    private static ArrayList<Ponto> getAlvosDisponiveis() {
        ArrayList<Ponto> alvosDisponiveis = new ArrayList<>(100);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                alvosDisponiveis.add(new Ponto(i, j));

            }
        }
        return alvosDisponiveis;
    }

    private void criaNovoTabuleiroJogador() {
        tabuleiroJogador = new Tabuleiro(10, 10);
        tabuleiroJogador.addNavios();

        socketServidor.setTabuleiro(tabuleiroJogador);
    }

    Celula serAtacado(Ponto ponto) throws IOException, InterruptedException {
        Celula celula = tabuleiroJogador.getCelula(ponto.getX(), ponto.getY());
        celula.setAtacado(true);

        saida.serAtacado(ponto);

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

    void vitoria() {
        System.out.println("Você Venceu!");
    }

    void atualizaCelulaInimiga(Celula celula) throws IOException, InterruptedException {
        Celula celula1 = saida.getTabuleiroInimigo().getCelula(celula.getX(), celula.getY());
        celula1.setAtacado(true);
        celula1.setNavio(celula.getNavio());

        ultimaCelulaAtacada = celula1;

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
        Ponto alvo = null;
        if (ultimaCelulaAtacada != null && ultimaCelulaAtacada.isNavio()) {
            alvo = obterAlvoProximo(alvo, random);
        } else {
            alvo = alvosDisponiveis.remove(random.nextInt(alvosDisponiveis.size()));
        }

        System.out.println("Atacando: \"" + ((char) (alvo.getX() + 'A')) + "" + alvo.getY() + "\"");
        socketServidor.atacar(alvo);
    }

    private Ponto obterAlvoProximo(Ponto alvo, Random random) {
        Tabuleiro tabuleiroInimigo = saida.getTabuleiroInimigo();
        Celula[] vizinhos = getVizinhos(tabuleiroInimigo, ultimaCelulaAtacada);
        boolean encontrouVizinhoLivre = false;
        for (int i = 0; i < vizinhos.length && !encontrouVizinhoLivre; i++) {
            Celula vizinho = vizinhos[i];
            encontrouVizinhoLivre = !vizinho.isAtacado();
            if (encontrouVizinhoLivre) {
                alvo = removeAlvo(vizinho.getX(), vizinho.getY());
            }
        }

        //se não conseguir
        if (!encontrouVizinhoLivre) {
            alvo = alvosDisponiveis.remove(random.nextInt(alvosDisponiveis.size()));
        }
        return alvo;
    }

    private static Celula[] getVizinhos(Tabuleiro t, Celula c) {
        Celula[] cs;
        if (c.getX() == 0 && c.getY() == 0) {
            cs = new Celula[]{t.getCelula(1, 0), t.getCelula(0, 1)};
        } else if (c.getX() == 9 && c.getY() == 9) {
            cs = new Celula[]{t.getCelula(8, 9), t.getCelula(9, 8)};
        } else if (c.getX() == 0 && c.getY() == 9) {
            cs = new Celula[]{t.getCelula(1, 9), t.getCelula(0, 8)};
        } else if (c.getX() == 9 && c.getY() == 0) {
            cs = new Celula[]{t.getCelula(9, 1), t.getCelula(8, 0)};
        } else if (c.getX() == 0) {
            cs = new Celula[]{
                t.getCelula(c.getX() + 1, c.getY()),
                t.getCelula(c.getX(), c.getY() + 1),
                t.getCelula(c.getX(), c.getY() - 1),};
        } else if (c.getY() == 0) {
            cs = new Celula[]{
                t.getCelula(c.getX() + 1, c.getY()),
                t.getCelula(c.getX(), c.getY() + 1),
                t.getCelula(c.getX() - 1, c.getY())};
        } else if (c.getX() == 9) {
            cs = new Celula[]{
                t.getCelula(c.getX(), c.getY() + 1),
                t.getCelula(c.getX() - 1, c.getY()),
                t.getCelula(c.getX(), c.getY() - 1)};
        } else if (c.getY() == 9) {
            cs = new Celula[]{
                t.getCelula(c.getX() + 1, c.getY()),
                t.getCelula(c.getX() - 1, c.getY()),
                t.getCelula(c.getX(), c.getY() - 1)};
        } else {
            cs = new Celula[]{
                t.getCelula(c.getX() + 1, c.getY()),
                t.getCelula(c.getX(), c.getY() + 1),
                t.getCelula(c.getX() - 1, c.getY()),
                t.getCelula(c.getX(), c.getY() - 1)};
        }
        return cs;
    }

    private Ponto removeAlvo(int x, int y) {
        Ponto p = null;
        for (int i = 0; i < alvosDisponiveis.size(); i++) {
            p = alvosDisponiveis.get(i);
            if (p.getX() == x & p.getY() == y) {
                break;
            }
        }
        alvosDisponiveis.remove(p);
        return p;
    }

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
