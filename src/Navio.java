

public class Navio {

    private int tamanhoRestante;
    private final Celula[] celulas;    
    private final TipoNavio tipo;

    public Navio(TipoNavio tipo, Tabuleiro tabuleiro) {
        this.tipo = tipo;
        this.tamanhoRestante = tipo.getTamanho();
        Celula[] espaço = NavioBuilder.obtemEspaçoVazio(tipo, tabuleiro);
        alocaEspaço(espaço);
        this.celulas = espaço;
    }

    public int getTamanhoRestante() {
        return tamanhoRestante;
    }

    public void setTamanhoRestante(int tamanho) {
        this.tamanhoRestante = tamanho;
    }

    public Celula[] getCelulas() {
        return celulas;
    }
    
    private void alocaEspaço(Celula[] espaço) {
        for (Celula celula : espaço) {
            celula.setNavio(this);
        }
    }

    public void subtraiTamanhoRestante(){
        this.tamanhoRestante -= 1;
    }
    
    public boolean estaVivo(){
        return tamanhoRestante > 0;
    }

    public TipoNavio getTipo() {
        return tipo;
    }
    
    
    
}
