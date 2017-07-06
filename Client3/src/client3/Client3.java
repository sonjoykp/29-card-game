package client3;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Client3 implements Runnable {

    // The client socket

    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream os = null;
    // The input stream
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    public String card1[] = {"jc", "9c", "ac", "tc",
        "kc", "qc", "8c", "7c", "jh", "9h", "ah", "th", "kh", "qh", "8h", "7h", "js", "9s", "as", "ts", "ks", "qs", "8s", "7s", "jd", "9d", "ad", "td", "kd", "qd", "8d", "7d"
    };
    
    static Frame3 f3 = new Frame3();

    public void oos(String s) {
        os.println(s);
    }
    public void slp()
    {
        try {
            sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        f3.setVisible(true);
        int portNumber = 2222;
        // The default host.
        String host = "localhost";

        if (args.length < 2) {
            System.out
                    .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
                            + "Now using host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.parseInt(args[1]);
        }

        /*
         * Open a socket on a given host and port. Open input and output streams.
         */
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }

       
        if (clientSocket != null && os != null && is != null) {
            try {

                /* Create a thread to read from the server. */
                new Thread(new Client3()).start();
                while (!closed) {

                    os.println(inputLine.readLine().trim());
                }
                /*
                 * Close the output stream, close the input stream, close the socket.
                 */
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    
    public void run() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
        String responseLine;
        
        try {
            int k = 0;
            while ((responseLine = is.readLine()) != null) {
                
                boolean string = true;
                for (int i = 0; i < 32; i++) {
                    if (card1[i].equals(responseLine)) {
                        f3.printy(responseLine, k);
                        //System.out.println("k3="+k);
                        k++;
                        if(k>7)k=0;
                        string = false;
                    }
                }
                if (string) {
                     if (responseLine.startsWith("p1") || responseLine.startsWith("p2")||responseLine.startsWith("p4") ) {
                        f3.setp2(responseLine);
                    } 
                     else if(responseLine.startsWith("pass"))
                    {
                        f3.paneloff();
                    }
                     else if (responseLine.startsWith("tr")) {
                       // trump=responseLine.substring(2);
                        f3.trumpiconset(responseLine.charAt(2));
                        
                    }
                     else if (responseLine.startsWith("op")) {
                        f3.showicon();
                    }
                     else if (responseLine.startsWith("on")) {
                        f3.on();
                    }
                     else if (responseLine.startsWith("Lastwinner")) {
                        f3.showwinner(responseLine);
                    }
                     else if(responseLine.startsWith("Player 3 took"))
                    {
                        f3.showtrump();
                    }
                    else if(responseLine.startsWith("new"))
                    {
                        f3.panelon();
                    }
                     else {
                        System.out.println(responseLine);
                    }
             
                }
               
            }
            closed = true;

        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
