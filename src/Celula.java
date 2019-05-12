
class Celula {
    private int x;
    private int y;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public Celula(int x, int y, Navio navio) {
        this.x = x;
        this.y = y;
        this.navio = navio;
    }
    
    
}
