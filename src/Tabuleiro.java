
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Tabuleiro {

    private final Celula[][] tabuleiro;
    private final int largura;
    private final int altura;

    public Tabuleiro(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;

        this.tabuleiro = Tabuleiro.constroiCelulas(largura, altura);
    }

    private static Celula[][] constroiCelulas(int largura, int altura) {
        Celula[][] tabuleiro = new Celula[largura][altura];
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                tabuleiro[i][j] = new Celula(i, j);
            }
        }
        return tabuleiro;
    }

    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public void addNaviosDeArquivo() {
        File f = new File("tabuleiro_cliente.txt");
        try {
            Scanner s = new Scanner(f);
            TipoNavio tipos[] = TipoNavio.values();
            int quantidade[] = new int[tipos.length];

            Navio[] navios = new Navio[TipoNavio.getNumeroNavios()];
            for (int i = 0; i < navios.length; i++) {
                String linha = s.nextLine();

                TipoNavio tipo = TipoNavio.tipoForChar(linha.charAt(0));
                int x = linha.charAt(1) - 'A';
                int y = linha.charAt(2) - '0';
                boolean rotacao = linha.charAt(3) == 'V';
                Navio navio = new Navio(tipo, x, y, rotacao, this);
                navios[i] = navio;
                quantidade[tipo.ordinal()]++;
            }

            for (int i = 0; i < quantidade.length; i++) {
                if (quantidade[i] != tipos[i].getQuantidade()) {
                    throw new IllegalStateException("Entrada invalida");
                }

            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void addNavios() {

        TipoNavio tipos[] = TipoNavio.values();
        Navio[][] navios = new Navio[tipos.length][];

        for (int j = 0; j < tipos.length; j++) {
            TipoNavio tipo = tipos[j];

            Navio[] naviosTipo = new Navio[tipo.getQuantidade()];
            for (int i = 0; i < tipo.getQuantidade(); i++) {
                naviosTipo[i] = new Navio(tipo, this);
            }

            navios[j] = naviosTipo;
        }
    }

    public Celula getCelula(int x, int y) {
        return tabuleiro[x][y];
    }

    public int getNumeroCelulasMortasNavios() {
        int celulasMortas = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                Celula celula = getCelula(i, j);

                if (celula.isAtacado() && celula.isNavio()) {
                    celulasMortas++;
                }
            }
        }

        return celulasMortas;
    }

    public static int getNumeroCelulasNavios() {
        return TipoNavio.getNumeroCelulasNavios();
    }

    public void imprime() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String str = "    0  1  2  3  4  5  6  7  8  9\n\n";
        for (int i = 0; i < tabuleiro.length; i++) {
            char c = (char) (i + 'A');
            str += (c + "   ");
            for (Celula item : tabuleiro[i]) {
                str += (item + "  ");
            }
            str += "\n";

        }
        return str;

    }

    public void imprimeRevelado() {
        String str = "    0  1  2  3  4  5  6  7  8  9\n\n";
        for (int i = 0; i < tabuleiro.length; i++) {
            char c = (char) (i + 'A');
            str += (c + "   ");
            for (Celula item : tabuleiro[i]) {
                if (item.isAtacado() && item.isNavio()) {
                    str += item.getNavio().getTipo().getTipo() + "  ";
                } else {
                    str += (item + "  ");
                }
            }
            str += "\n";

        }
        System.out.println(str);
    }

}
