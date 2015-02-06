
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class GuiClient {
    
    private JTextArea chatTextarea;
    private String user = "alex";
    private ClientPeer client;
    private Thread listening; 
    private LatexExample frame;
    
    private String IP;
    private int PORT;
    
    public GuiClient(){
        
        frame = new LatexExample();
        
        final JFrame server = new JFrame("Server host");
        server.setLayout( new GridLayout(4,1) );
        JLabel hostLabel = new JLabel("Please enter host details below.");
        final JTextField ipTxt = new JTextField(30);
        ipTxt.setText("127.0.0.1");
        final JTextField portTxt = new JTextField(30);
        portTxt.setText("9000");
        JButton connectBtn = new JButton("Connect");
        server.add(hostLabel);
        server.add(ipTxt);
        server.add(portTxt);
        server.add(connectBtn);
        server.pack();
        server.setSize(250, 150);
        server.setVisible(true);
        
        final JFrame window = new JFrame( "Chat Client" );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setLayout( new BorderLayout() );
        
        JPanel north = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        final JTextField nameTxt = new JTextField(30);
        JButton changeNameBtn = new JButton("Change Name");
        north.add(nameLabel);
        north.add(nameTxt);
        north.add(changeNameBtn);
        
        chatTextarea = new JTextArea(20, 100);
        chatTextarea.setLineWrap(true);
        JScrollPane scrollingTexarea = new JScrollPane(chatTextarea);
        
        JPanel south = new JPanel();
        final JTextField messageTxt = new JTextField(40);
        JButton sendBtn = new JButton("Send");
        south.add(messageTxt);
        south.add(sendBtn);
        
        window.add(north, BorderLayout.NORTH);
        window.add(scrollingTexarea, BorderLayout.CENTER);
        window.add(south, BorderLayout.SOUTH);
        
        window.pack();
        window.setSize(600, 400);   
        
        chatTextarea.append("*************************************************\n*Wellcome ! Enter a username to start chattin."
        + "*\n*************************************************\n");
        
        ActionListener send = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if( client == null ){
                    chatTextarea.append("Please enter a name.\n");
                } else {
                    String message = messageTxt.getText();
                    if( !message.equals("") )
                        client.Send(message);
                    messageTxt.setText(null);
                }
            }
        };
        
        sendBtn.addActionListener(send);
        messageTxt.addActionListener(send);
        
        ActionListener name = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTxt.getText();
                if( name.equals("") ){
                    chatTextarea.append("Please enter a name.\n");
                } else {
                    user = name;
                    connect();
                    listen();
                }
            }
        
        };
        
        changeNameBtn.addActionListener(name);
        nameTxt.addActionListener(name);
        
        ActionListener details = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                IP = ipTxt.getText();
                PORT = Integer.parseInt(portTxt.getText());
                server.setVisible(false);
                window.setVisible(true);
            }
        };
        
        connectBtn.addActionListener(details);
        
        
    }
    
    public void connect(){
        String IP = this.IP;
        
        int PORT = this.PORT;
        try {
            
            Socket s = new Socket(IP, PORT);
            this.listening = null;
            client = null;
            client = new ClientPeer( user, s );
            //client.start();
            chatTextarea.append("You are now connected as "+user+".\n");
        } catch (Exception e) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, e);
        }
    
    }
    
    public static void main(String[] arg){
        GuiClient gui = new GuiClient();
        //gui.connect();
        //gui.listen();
    }

    private void listen() {
      // incepe ascultarea
      listening = new Thread(){
          public void run(){
              try {
                Message m;
                while( (m = (Message) client.ois.readObject()) != null ){
                    if( m instanceof PrivateMessage ){
                        PrivateMessage pm = (PrivateMessage) m;
                        if( pm.getDestinatar().equals(user) )
                           chatTextarea.append(m.format()+"\n"); 
                    } else if(m instanceof LatexMessage){
                        String math = m.format();
                        frame.render(math);
                        chatTextarea.append("LaTeX formula opening...\n");
                    } else {
                       chatTextarea.append(m.format()+"\n");
                    }
                }
            } catch (Exception e){
                Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, e);
            }
          }
      };
      listening.start();
    }  
}
