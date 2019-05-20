
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    private ConnectionServidor servidor;
    private final Socket socket;
    
    public Cliente(String ip, int port) throws IOException{
        this.socket = new Socket(ip, port);
    }
    
    
    /*
     public void connect(SeaBattleClient client) throws Exception {
        server = new ConnectionToServer(socket, client);
    }
    */
    
    public ConnectionServidor getConnection(){
        return this.servidor;
    }
}
