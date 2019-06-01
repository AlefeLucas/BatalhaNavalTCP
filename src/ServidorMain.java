
import java.io.IOException;


public class ServidorMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Servidor servidor = new Servidor(8080);        
        servidor.iniciar();
    }
}
