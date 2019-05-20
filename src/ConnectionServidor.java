
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


class ConnectionServidor  implements Runnable{
    private Socket socket;
    private ClienteBatalhaNaval cliente;
    
    private  Thread thread;

    private  ObjectInputStream input;
    private  ObjectOutputStream output;

    public ConnectionServidor(Socket socket, ClienteBatalhaNaval cliente) throws IOException {
        this.socket = socket;
        this.cliente = cliente;
        
         this.thread = new Thread(this);
        this.thread.start();

        output = new ObjectOutputStream(this.socket.getOutputStream());
        input = new ObjectInputStream(this.socket.getInputStream());
    }
    
    public void write(Object object) throws IOException{
        output.writeObject(object);
        output.flush();
    }

    
    
    
    @Override
    public void run() {
        /**
         * TODO PAREI AQUI
         */
        
        
    }
    
    
}
