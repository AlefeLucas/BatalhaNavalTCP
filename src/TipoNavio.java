
enum TipoNavio {
    PORTA_AVIÕES,
    NAVIO_TANQUE,
    CONTRATORPEDEIRO,
    SUBMARINO;

    public int getTamanho() {
        switch (this) {
            case PORTA_AVIÕES:
                return 5;
            case NAVIO_TANQUE:
                return 4;
            case CONTRATORPEDEIRO:
                return 3;
            case SUBMARINO:
                return 2;
        }
        
        throw new IllegalStateException("Tipo não tratado");
    }
    
     public char getTipo() {
        switch (this) {
            case PORTA_AVIÕES:
                return 'p';
            case NAVIO_TANQUE:
                return 't';
            case CONTRATORPEDEIRO:
                return 'c';
            case SUBMARINO:
                return 's';
        }

        throw new IllegalStateException("Tipo não tratado");
    }

    public int getQuantidade() {
        switch (this) {
            case PORTA_AVIÕES:
                return 1;
            case NAVIO_TANQUE:
                return 2;
            case CONTRATORPEDEIRO:
                return 3;
            case SUBMARINO:
                return 4;
        }

        throw new IllegalStateException("Tipo não tratado");
    }
    
    public static int getNumeroCelulasNavios() {
        int celulasNavios = 0;
        TipoNavio[] values = TipoNavio.values();
        for (int i = 0; i < values.length; i++) {
            celulasNavios += getNumeroCelulasPorTipo(values[i]);
        }
        return celulasNavios;
    }

    private static int getNumeroCelulasPorTipo(TipoNavio tipoNavio) {
        return tipoNavio.getTamanho()* tipoNavio.getQuantidade();
    }
   
    

}
