
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientPeer extends Thread {

    public String username;
    private ObjectOutputStream oos;
    public ObjectInputStream ois;
    
    
    
    public ClientPeer( final String username, Socket s ) throws IOException{
        this.username = username;
        this.oos = new ObjectOutputStream( s.getOutputStream() );
        this.ois = new ObjectInputStream( s.getInputStream() );
    }
    
    public void run() {
	try{
	String line;
        while( true ){
           line = System.console().readLine();
           if( isPrivateMessage(line) )
               this.SendMessage( getReceiver(line), getMessage(line) );
           else
               this.SendMessage( line );
        }
	} catch (Exception e) {}
    }
    
    public void SendMessage( String message ) throws IOException{
        this.oos.writeObject( new Message( this.username, message ) );
    }
    
    public void SendMessage( String message, String destinatar ) throws IOException{
        this.oos.writeObject( new PrivateMessage( this.username, destinatar, message ) );
    }
    
    public void SendLatexMessage(String latex) throws IOException{
        this.oos.writeObject( new LatexMessage( this.username, latex ) );
    }

    public static boolean isPrivateMessage( String line ){
        return line.contains("/w");
    }
    
    public static boolean isLatexMessage( String line ){
        return line.contains("/latex");
    }
    
    public static String getReceiver( String line ){
        return line.trim().split( " ", 3 )[1];
    }
    
    public static String getMessage( String line ){
        return line.trim().split( " ", 3 )[2];
    }

    void Send(String line) {
        try {
            if( isPrivateMessage(line) ){
                this.SendMessage( getReceiver(line), getMessage(line) );
            } else if(isLatexMessage(line)){
                String l = line.trim().split( " ", 2 )[1];
                this.SendLatexMessage(l);
            } else {
                this.SendMessage( line );
            }
        } catch (Exception e){
        }
    }
    
    
}
