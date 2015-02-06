
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerPeer extends Thread {
    
    private ObjectInputStream ois;
    private ArrayList<ServerPeer> clients;
    public ObjectOutputStream oos;
    
    public void run(){
        try {
            // citeste si distribuie mesaje
            Message m;
            while( ( m = (Message) ois.readObject() ) != null ){
                
                System.out.println(m.format());
                
                synchronized(ServerPeer.class){
                    for( ServerPeer peer : this.clients )
                        //if(peer != this)
                            peer.oos.writeObject(m);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            remove();
        } catch (IOException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void add(){
        synchronized(ServerPeer.class){
            clients.add(this);
        }
    }
    
    private void remove() throws IOException{
        synchronized(ServerPeer.class){
            this.oos.close();
            clients.remove(this);
        }
    }

    public ServerPeer(Socket s, ArrayList clients) throws IOException, ClassNotFoundException{
        this.clients = clients;
        this.ois = new ObjectInputStream( s.getInputStream() );
        this.oos = new ObjectOutputStream( s.getOutputStream() );
        //oos.writeObject(new Message("", "welcome to chat"));
        add();
    }
    
}
