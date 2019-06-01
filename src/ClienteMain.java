

public class ClienteMain {
    public static void main(String[] args) {
        try {
            JogoCliente jogo = new JogoCliente(args[0], Integer.parseInt(args[1]));
            SaidaCliente saida = new SaidaCliente();
            saida.aguardando();
            saida.setJogo(jogo);
            jogo.setSaida(saida);
            
            saida.renderizarTabuleiroJogador();
            saida.renderizarTabuleiroInimigo();
            saida.imprime();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        } 
    }
}
