
import java.util.Random;

public class NavioBuilder {

    private static final Random RANDOM = new Random();

    private static Celula obtemCelulaVaziaAleatoria(Tabuleiro tabuleiro) {
        while (true) {
            int x = RANDOM.nextInt(tabuleiro.getLargura());
            int y = RANDOM.nextInt(tabuleiro.getAltura());
            Celula celula = tabuleiro.getTabuleiro()[x][y];

            if (!celula.isNavio()) {
                return celula;
            }
        }
    }

    private static Celula[] obtemPossivelPosição(Celula celulaInicial, boolean orientação, TipoNavio tipo, Tabuleiro tabuleiro) {
        Celula[] celulas = new Celula[tipo.getTamanho()];
        celulas[0] = celulaInicial;
        if (orientação) {
            //horizontal
            if (tipo.getTamanho() + celulaInicial.getX() > tabuleiro.getLargura()) {
                //alocar a esquerda
                for (int i = 1; i < tipo.getTamanho(); i++) {
                    celulas[i] = tabuleiro.getTabuleiro()[celulaInicial.getX() - i][celulaInicial.getY()];
                }
            } else {
                //alocar a direita
                for (int i = 1; i < tipo.getTamanho(); i++) {
                    celulas[i] = tabuleiro.getTabuleiro()[celulaInicial.getX() + i][celulaInicial.getY()];
                }
            }
        } else {
            //vertical
            if (tipo.getTamanho() + celulaInicial.getY() > tabuleiro.getAltura()) {
                //alocar acima
                for (int i = 1; i < tipo.getTamanho(); i++) {
                    celulas[i] = tabuleiro.getTabuleiro()[celulaInicial.getX()][celulaInicial.getY() - i];
                }
            } else {
                //alocar abaixo
                for (int i = 1; i < tipo.getTamanho(); i++) {
                    celulas[i] = tabuleiro.getTabuleiro()[celulaInicial.getX()][celulaInicial.getY() + i];
                }
            }
        }

        return celulas;
    }

    public static boolean estaDisponivel(Celula[] possivelPosição) {
        boolean estaDisponivel = true;
        for (Celula celula : possivelPosição) {
            estaDisponivel &= !celula.isNavio();
        }
        return estaDisponivel;
    }

    public static Celula[] obtemEspaco(TipoNavio tipo, Tabuleiro tabuleiro, int x, int y, boolean rotacao) {
        Celula celulaInicial = obtemCelula(tabuleiro, x, y);
        Celula[] posicao = obtemPosiçãoDeAcordo(celulaInicial, rotacao, tipo, tabuleiro);
        if(estaDisponivel(posicao)){
            return posicao;
        } else {
            throw new RuntimeException("Tem navio aqui");
        }
    }

    public static Celula obtemCelula(Tabuleiro tabuleiro, int x, int y) {
        Celula celula = tabuleiro.getTabuleiro()[x][y];

        if (!celula.isNavio()) {
            return celula;
        } else {
            throw new RuntimeException("Tem navio aqui");
        }
    }

    public static Celula[] obtemEspaçoVazio(TipoNavio tipo, Tabuleiro tabuleiro) {
        while (true) {
            boolean orientação = orientaçãoAleatoria();
            Celula celulaVazia = obtemCelulaVaziaAleatoria(tabuleiro);

            Celula[] possivelPosição = obtemPossivelPosição(celulaVazia, orientação, tipo, tabuleiro);
            if (estaDisponivel(possivelPosição)) {
                return possivelPosição;
            }
        }
    }

    private static boolean orientaçãoAleatoria() {
        return RANDOM.nextBoolean();
    }

    private static Celula[] obtemPosiçãoDeAcordo(Celula celulaInicial, boolean rotacao, TipoNavio tipo, Tabuleiro tabuleiro) {
       Celula [] celulas = new Celula[tipo.getTamanho()];
       celulas[0] = celulaInicial;
        for (int i = 1; i < celulas.length; i++) {
            if(rotacao){
                celulas[i] = tabuleiro.getCelula(celulaInicial.getX() + i, celulaInicial.getY());
            } else {
                celulas[i] = tabuleiro.getCelula(celulaInicial.getX(), celulaInicial.getY() + i);
            }
            
        }
        
        return celulas;
    }
}
