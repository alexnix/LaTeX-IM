
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {
        ArrayList clients = new ArrayList<ServerPeer>();
        
        try {
            ServerConfig conf = new ServerConfig();
            ServerSocket ss = new ServerSocket( conf.getTCPPort() );
            int MAX_CLIENTS = conf.getMaxClient();
            while(true){
                if( clients.size() < MAX_CLIENTS ){
                    ServerPeer serverClient = new ServerPeer(ss.accept(), clients);
                    serverClient.start();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MissingKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
