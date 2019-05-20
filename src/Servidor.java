
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private final ServerSocket serverSocket;
    private final List<ConnectionCliente> clientes;
    
    public Servidor(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        clientes = new ArrayList<>(2);
    }

    public ConnectionCliente getOutroCliente(ConnectionCliente cliente) {
        for (ConnectionCliente connection : clientes) {
            if(connection != cliente) return connection;
        }
        
        throw new IllegalStateException("Não foi possível obter outro cliente");
    }
}
