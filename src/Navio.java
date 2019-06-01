
import java.io.Serializable;



public class Navio implements Serializable{

    private final Celula[] celulas;    
    private final TipoNavio tipo;

    public Navio(TipoNavio tipo, Tabuleiro tabuleiro) {
        this.tipo = tipo;
        //this.tamanhoRestante = tipo.getTamanho();
        Celula[] espaço = NavioBuilder.obtemEspaçoVazio(tipo, tabuleiro);
        alocaEspaço(espaço);
        this.celulas = espaço;
    }
    
     public Navio(TipoNavio tipo, int x, int y, boolean orientacao, Tabuleiro tabuleiro) {
        this.tipo = tipo;
        //this.tamanhoRestante = tipo.getTamanho();
        Celula[] espaço = NavioBuilder.obtemEspaco(tipo, tabuleiro, x, y, orientacao);
        alocaEspaço(espaço);
        this.celulas = espaço;
    }

    private void alocaEspaço(Celula[] espaço) {
        for (Celula celula : espaço) {
            celula.setNavio(this);
        }
    }

    public TipoNavio getTipo() {
        return tipo;
    }
    
    
    
}
