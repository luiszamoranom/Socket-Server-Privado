package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 *
 * @author RPVZ
 */
public class HiloDeCliente implements Runnable, ListDataListener{
    private DefaultListModel mensajes;
    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    public HiloDeCliente(DefaultListModel mensajes, Socket socket){
        this.mensajes = mensajes;
        this.socket = socket;
        try{
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            mensajes.addListDataListener(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            while (true){
                String texto = dataInput.readUTF();
                synchronized (mensajes){
                    mensajes.addElement(texto);
                    System.out.println(texto);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void intervalAdded(ListDataEvent e){
        String texto = (String) mensajes.getElementAt(e.getIndex0());
        try{
            dataOutput.writeUTF(texto);
        } catch (Exception excepcion){
            excepcion.printStackTrace();
        }
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
