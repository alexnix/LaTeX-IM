
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ServerConfig {
    
    private final static String DEFAULT_CONFIG_FILE = "server.conf";
    private final int UNKNOWN = -1;
    private final String COMMENT_LINE = "#";
    private final String ASSIGN_OPERATOR = "=";
    private final String INVALID_FORMAT_EXCEPTION_MESSAGE = "Am depistat o linie care nu respecta formatul.";
    private final String UNKNOWN_KEY_EXCEPTION_MESSAGE = "Am depistat o cheie necunoscuta, de la o usa necunoscuta !";
    private final String MISSING_KEY_EXCEPTIO_MESSAGE = "Una dintre chei lipseste.";
    private final String TCP_PORT_LABEL = "TCP_PORT";
    private final String MAX_CLIENT_LABEL = "MAX_CLIENT";
    
    private int tcp_port = UNKNOWN;
    private int max_client = UNKNOWN;
    
    public ServerConfig(String file) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        parceFile(file);
        
        if( tcp_port == UNKNOWN || max_client == UNKNOWN ) {
            throw new MissingKeyException( this.MISSING_KEY_EXCEPTIO_MESSAGE );
        }
    }
    
    public ServerConfig() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        this(DEFAULT_CONFIG_FILE);
    }
    
    private void parceFile(String file) throws IOException, InvalidFormatException, UnknownKeyException {
        
        BufferedReader f = new BufferedReader(new FileReader(file));
        String line;
        
        while( ( line = f.readLine() ) != null ){
            line = line.trim();
            
            if( !line.startsWith(this.COMMENT_LINE) && line.length()!=0 ) {
                
                if( line.contains(this.ASSIGN_OPERATOR) && !line.startsWith(ASSIGN_OPERATOR)){
                    String[] l = line.split(this.ASSIGN_OPERATOR);
                    if( l[0].equals(this.TCP_PORT_LABEL) ){
                        tcp_port = Integer.parseInt( l[1] );
                    } else if ( l[0].equals(this.MAX_CLIENT_LABEL) ) {
                        max_client = Integer.parseInt( l[1] );
                    } else {
                        throw new UnknownKeyException(this.UNKNOWN_KEY_EXCEPTION_MESSAGE);
                    }
                } else {
                    throw new InvalidFormatException(this.INVALID_FORMAT_EXCEPTION_MESSAGE);
                }
                
            }
            
        }
    }
    
    public int getTCPPort(){
        return this.tcp_port;
    }
    
    public int getMaxClient() {
        return this.max_client;
    }
}
