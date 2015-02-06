
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextClient {
    
    public static void main( String[] args ){
        String IP = "127.0.0.1";
        String user = "alex";
        int PORT = 8000;
        try {
            
            Socket s = new Socket(IP, PORT);
            ClientPeer client = new ClientPeer( user, s );
            client.start();
            // incepe ascultarea
            try {
                Message m;
                while( (m = (Message) client.ois.readObject()) != null ){
                    if( m instanceof PrivateMessage ){
                        PrivateMessage pm = (PrivateMessage) m;
                        if( pm.getDestinatar().equals(user) )
                           System.out.println(m.format()); 
                    } else {
                        System.out.println(m.format());
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
