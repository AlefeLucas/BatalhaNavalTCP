
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    void iniciar() throws IOException, InterruptedException {
        Socket socket;

        while(clientes.size() < 2){
            System.out.println("Aguardando cliente... " + clientes.size() + " de 2.");
            socket = serverSocket.accept();

            System.out.println("Obteve cliente.");
            clientes.add(new ConnectionCliente(clientes.size() + 1, socket, this));
        }

        System.out.println("2 de 2.");
        
        System.out.println("Delay 1 segundo\n");
        TimeUnit.SECONDS.sleep(1);
        clientes.get(0).write(true);
        clientes.get(1).write(false);
    }
}
