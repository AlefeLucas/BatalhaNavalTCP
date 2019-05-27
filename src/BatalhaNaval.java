
import java.io.IOException;


public class BatalhaNaval {
    public static final int TAMANHO_TABULEIRO = 10;
    private Tabuleiro tabuleiroJogador;
    private ClienteBatalhaNaval socketCliente;
    
    public BatalhaNaval() throws IOException{
        socketCliente = new ClienteBatalhaNaval("127.0.0.1", 8080);
        socketCliente.setJogo(this);
        
        criaNovoTabuleiroJogador();
    }
    
    private void criaNovoTabuleiroJogador() {
        tabuleiroJogador = new Tabuleiro(TAMANHO_TABULEIRO, TAMANHO_TABULEIRO);
        tabuleiroJogador.addNavios();
        
        socketCliente.setTabuleiro(tabuleiroJogador);
    }

    Celula serAtacado(Ponto ponto) {
        Celula celula = tabuleiroJogador.getCelula(ponto.getX(), ponto.getY());
        celula.setAtacado(true);
        
        if(celula.isNavio()){
            Navio navio = celula.getNavio();
            navio.subtraiTamanhoRestante();
            
            if(!navio.estaVivo()){
                //TODO printa tabuleiros
            }
        } 
        
        // TODO printa tabuleiros
        
        if(verificaFimJogo()){
            perdeu();
            enviaVitoria();
        }
        
        return celula;
    }

    void vitoria() {
        System.out.println("Você Venceu!");
    }

    void atualizaCelulaInimiga(Celula celula) {
        /**
         * TODO
         * So faz sentido esse método com GUI
         * Imprimir tabuleiros?
         */
    }

    private boolean verificaFimJogo() {
        return tabuleiroJogador.getNumeroCelulasMortasNavios() == tabuleiroJogador.getNumeroCelulasNavios();
    }

    private void perdeu() {
        System.out.println("Você Perdeu!");
    }

    private void enviaVitoria() {
        socketCliente.enviaVitoria();
    }

    
    
}
