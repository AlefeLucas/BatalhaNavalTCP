
class Tabuleiro {
    private final Celula[][] tabuleiro;    
    private final int largura;
    private final int altura;

    public Tabuleiro(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        
        this.tabuleiro = constroiCelulas(largura, altura);      
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

   
    
}
