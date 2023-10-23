package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.DefaultListModel;
/**
 *
 * @author RPVZ
 */
public class ServidorChat{
    private DefaultListModel mensajes = new DefaultListModel();
    public static void main(String[] args){
        new ServidorChat();
    }
    public ServidorChat(){
        try{
            ServerSocket socketServidor = new ServerSocket(5000);
            while (true){
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente =  new HiloDeCliente(mensajes, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
