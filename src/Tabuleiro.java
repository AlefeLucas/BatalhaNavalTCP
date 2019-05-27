
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
    
//    public static Navio[] fabricaNavio(){
//        
//    }

    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    void addNavios() {
        
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

    Celula getCelula(int x, int y) {
           return tabuleiro[x][y];
    }

    public int getNumeroCelulasMortasNavios() {
        int celulasMortas = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                Celula celula = getCelula(i, j);
                
                if(celula.isAtacado() && celula.isNavio())
                    celulasMortas++;
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
    
    
    
}
