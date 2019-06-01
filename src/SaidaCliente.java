
import java.io.IOException;
import java.util.Scanner;

class SaidaCliente {

    private JogoCliente jogo;
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroInimigo;
    private final Scanner scanner;
    private boolean pedindoAtaque;

    public SaidaCliente() {
        System.out.println("Jogo Batalha Naval TCP com um servidor e dois clientes.");
        scanner = new Scanner(System.in);
    }

    void setJogo(JogoCliente jogo) {
        this.jogo = jogo;
    }

    void renderizarTabuleiroJogador() {
        this.tabuleiroJogador = jogo.getTabuleiroJogador();

    }

    void renderizarTabuleiroInimigo() {
        this.tabuleiroInimigo = new Tabuleiro(10, 10);

    }

    void imprime() {
        System.out.println("\nSEU TABULEIRO:");
        System.out.println(this.tabuleiroJogador);
        System.out.println("");
        System.out.println("TABULEIRO INIMIGO:");
        System.out.println(this.tabuleiroInimigo);
        System.out.println("");
    }

    void pedirPontoAtaque() throws IOException {
        if (pedindoAtaque) {
            System.out.println("Digite coordenada para atacar (ex: A1): ");
        } else {
            Thread threadPedirAtaque = new Thread(() -> {
                try {
                    boolean entrou = false;
                    while (!entrou) {
                        System.out.print("\nDigite entrada:\n  P - imprimir tabuleiros\n  Coordenada para atacar (ex: A1)\nEntrada: ");

                        pedindoAtaque = true;
                        String linha = scanner.nextLine().toUpperCase();
                        pedindoAtaque = false;

                        if (linha.equalsIgnoreCase("P")) {
                            imprime();
                        } else {
                            entrou = processaEntrada(linha);
                            if (!entrou) {
                                System.out.println("Input incorreto.");
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            threadPedirAtaque.start();
        }
    }

    private boolean processaEntrada(String linha) throws IOException {
        boolean entrou = false;
        if (linha.length() == 2) {

            int x = linha.charAt(0) - 'A';
            int y = linha.charAt(1) - '0';
            if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
                System.out.println("Atacando: \"" + linha + "\"");

                jogo.atacar(x, y);
                entrou = true;
            }
        }
        return entrou;
    }

    public Tabuleiro getTabuleiroInimigo() {
        return tabuleiroInimigo;
    }

    void notificarIniciou(boolean vez) throws IOException {
        System.out.println("Adversário encontrado.\nPartida iniciada.\n");
        if (vez) {
            this.pedirPontoAtaque();
        } else {
            System.out.println("O adversário inicia...\n");
        }
    }

    void aguardando() {
        System.out.println("Aguardando outro jogador.");
    }

    void serAtacado(Ponto ponto) {
        System.out.println("Ataque sofrido em \"" + (char) (ponto.getX() + 'A') + "" + ponto.getY() + "\"\n");
    }

    void feedbackAtaque(boolean isNavio) {
        System.out.println((isNavio ? "Acertou navio!" : "Acertou água.") + "\n");
    }

}
