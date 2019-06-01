
import java.io.Serializable;


class Celula implements Serializable {
    private final int x;
    private final int y;
    private boolean atacado;
    private Navio navio;

    public Navio getNavio() {
        return navio;
    }

    public void setNavio(Navio navio) {
        this.navio = navio;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAtacado() {
        return atacado;
    }

    public void setAtacado(boolean atacado) {
        this.atacado = atacado;
    }
    
    public boolean isNavio(){
        return navio != null;
    }

    public Celula(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        if(this.isNavio() && !this.isAtacado()){
            return this.getNavio().getTipo().getTipo() + "";
        } else if(this.isNavio()){
            return "#";
        } else if(this.isAtacado()){
            return "•";
        } else {
            return " ";
        }
        
    }
    
    
    
}
