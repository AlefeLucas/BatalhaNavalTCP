/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alefe
 */
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
    
   

}
