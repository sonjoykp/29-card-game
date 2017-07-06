
package server1;

import java.awt.Frame;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import static java.lang.String.valueOf;
import java.net.Socket;

/**
 *
 * @author MRINAL
 */
public class ClientThread extends Thread implements Serializable {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final ClientThread[] threads;
    private int maxClientsCount;
    static Deck d = new Deck();
    static int m = 0, k = 0, cal = 0, n = 1;
    static PlayerHands p = new PlayerHands(d, 8);

    static String pl[] = new String[4];
    static String big = null;
    static int cou = 0;
    static int point[] = new int[4];
    static int hands = 0;
    static int lw = 0;
    static boolean[] on = {true, true, false, false};
    int pn[] = {3, 2, 1, 1, 0, 0, 0, 0};
    static int pg = 0;
    static int max = -1;
    static int wp = 0;
    static String bet = null;
    static boolean istrump = false;
    static int pt[]={0,0,0,0};

    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void sendAll() {

    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ClientThread[] threads = this.threads;

        try {
            /*
             * Create input and output streams for this client.
             */
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            os.println("Enter your name.");
            String name = is.readLine().trim();
            os.println("Hello " + name
                    + " to our chat room.\nTo leave enter /quit in a new line");

            Card[][] po = new Card[4][8];
            po[0] = p.playerOneCards;
            po[1] = p.playerTwoCards;
            po[2] = p.playerThreeCards;
            po[3] = p.playerFourCards;
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("*** A new user " + name
                            + " entered the chat room !!! ***");
                    //threads[i].os.println("Player 2:" + name);}

                    for (int l = 0; l < 8; l++) {
                        threads[i].os.println(po[i][l]);
                    }
                }
                k++;

            }

            char na[] = {'j', '9', 'a', 't', 'k', 'q', '8', '7'};
            while (true) {

                String line = is.readLine();
                if (line.startsWith("/quit")) {
                    break;
                } else if (line.startsWith("1b")) {
                    if (on[0]) {
                        if (line.startsWith("1bpass")) {
                            os.println("pass");
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 1 passed");
                                }
                            }
                            cal++;
                            on[0] = false;
                            if (cal != 3) {
                                if (on[m] == false) {
                                    if (m < n) {
                                        m = n + 1;
                                        on[m] = true;
                                    } else if (m > n) {
                                        m = m + 1;
                                        on[m] = true;
                                    }

                                } else if (on[n] == false) {
                                    if (m < n) {
                                        n = n + 1;
                                        on[n] = true;
                                    } else if (m > n) {
                                        n = m + 1;
                                        on[n] = true;
                                    }

                                }
                            } else if (cal == 3) {
                                for (int i = 0; i < 4; i++) {
                                    if (on[i] == true) {
                                        wp = i + 1;
                                        for (int j = 0; j < maxClientsCount; j++) {

                                            if (threads[j] != null) {
                                                if (max == -1) {
                                                    max = 16;
                                                }
                                                threads[j].os.println("Player " + String.valueOf(i + 1) + " took the bet,his bet: " + max);
                                                threads[j].os.println("pass");
                                            }
                                        }
                                    }

                                }
                            }
                        } else if (max < Integer.valueOf(line.substring(2))) {
                            max = Integer.valueOf(line.substring(2));
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 1 bet: " + max);
                                }
                            }
                        }
                    }
                } else if (line.startsWith("2b")) {
                    if (on[1]) {
                        if (line.startsWith("2bpass")) {
                            os.println("pass");
                            on[1] = false;
                            cal++;
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 2 passed");
                                }
                            }
                            if (cal != 3) {
                                if (on[m] == false) {
                                    if (m < n) {
                                        m = n + 1;
                                        on[m] = true;
                                    } else if (m > n) {
                                        m = m + 1;
                                        on[m] = true;
                                    }

                                } else if (on[n] == false) {
                                    if (m < n) {
                                        n = n + 1;
                                        on[n] = true;
                                    } else if (m > n) {
                                        n = m + 1;
                                        on[n] = true;
                                    }

                                }

                            }

                            if (cal == 3) {
                                for (int i = 0; i < 4; i++) {
                                    if (on[i] == true) {
                                        wp = i + 1;
                                        for (int j = 0; j < maxClientsCount; j++) {
                                            if (threads[j] != null) {
                                                if (max == -1) {
                                                    max = 16;
                                                }
                                                threads[j].os.println("Player " + String.valueOf(i + 1) + " took the bet,his bet: " + max);
                                                threads[j].os.println("pass");
                                            }
                                        }
                                    }

                                }
                            }
                        } else if (max <= Integer.valueOf(line.substring(2))) {
                            max = Integer.valueOf(line.substring(2));
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 2 bet: " + max);
                                }
                            }
                        }
                    }
                } else if (line.startsWith("3b")) {
                    if (on[2]) {
                        if (line.startsWith("3bpass")) {
                            os.println("pass");
                            cal++;
                            on[2] = false;
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 3 passed");
                                }
                            }
                            if (cal != 3) {
                                if (on[m] == false) {
                                    if (m < n) {
                                        m = n + 1;
                                        on[m] = true;
                                    } else if (m > n) {
                                        m = m + 1;
                                        on[m] = true;
                                    }

                                } else if (on[n] == false) {
                                    if (m < n) {
                                        n = n + 1;
                                        on[n] = true;
                                    } else if (m > n) {
                                        n = m + 1;
                                        on[n] = true;
                                    }

                                }
                            }

                            if (cal == 3) {
                                for (int i = 0; i < 4; i++) {

                                    if (on[i] == true) {
                                        wp = i + 1;
                                        for (int j = 0; j < maxClientsCount; j++) {
                                            if (threads[j] != null) {
                                                if (max == -1) {
                                                    max = 16;
                                                }
                                                threads[j].os.println("Player " + String.valueOf(i + 1) + " took the bet,his bet: " + max);
                                                threads[j].os.println("pass");
                                            }
                                        }
                                    }

                                }
                            }
                        } else if (max <= Integer.valueOf(line.substring(2))) {
                            max = Integer.valueOf(line.substring(2));
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 3 bet: " + max);
                                }
                            }
                        }
                    }
                } else if (line.startsWith("4b")) {
                    if (on[3]) {
                        if (line.startsWith("4bpass")) {
                            os.println("pass");
                            on[3] = false;
                            cal++;
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 4 passed");
                                }
                            }
                            if (cal != 3) {
                                if (on[m] == false) {
                                    if (m < n) {
                                        m = n + 1;
                                        on[m] = true;
                                    } else if (m > n) {
                                        m = m + 1;
                                        on[m] = true;
                                    }

                                } else if (on[n] == false) {
                                    if (m < n) {
                                        n = n + 1;
                                        on[n] = true;
                                    } else if (m > n) {
                                        n = m + 1;
                                        on[n] = true;
                                    }

                                }
                            }
                            if (cal == 3) {
                                for (int i = 0; i < 4; i++) {
                                    if (on[i] == true) {
                                        wp = i + 1;
                                        for (int j = 0; j < maxClientsCount; j++) {
                                            if (threads[j] != null) {
                                                if (max == -1) {
                                                    max = 16;
                                                }
                                                threads[j].os.println("Player " + String.valueOf(i + 1) + " took the bet,his bet: " + max);
                                                threads[j].os.println("pass");
                                            }
                                        }
                                    }

                                }
                            }
                        } else if (max <= Integer.valueOf(line.substring(2))) {
                            max = Integer.valueOf(line.substring(2));
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null) {
                                    threads[i].os.println("Player 4 bet: " + max);
                                }
                            }
                        }
                    }
                } else if (line.startsWith("tr")) {
                    bet = line.substring(2);
                    System.out.println("bet=" + bet);
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            threads[i].os.println(line);
                            threads[i].os.println("on");
                            //threads[i].os.println("Player " + wp + " set trump card " + line);
                        }
                    }
                } else if (line.startsWith("op")) {
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            threads[i].os.println(line);
                        }
                    }
                } else if (line.startsWith("p1") || line.startsWith("p2") || line.startsWith("p3") || line.startsWith("p4")) {
                    if (line.startsWith("p1")) {
                        pl[0] = line.substring(2);
                    } else if (line.startsWith("p2")) {
                        pl[1] = line.substring(2);
                    } else if (line.startsWith("p3")) {
                        pl[2] = line.substring(2);
                    } else if (line.startsWith("p4")) {
                        pl[3] = line.substring(2);
                    }
                    cou++;
                    System.out.println("count=" + cou);
                    if (cou == 4) {

                        pg = 0;
                        char ch = pl[lw].charAt(1);
                        big = pl[lw];
                        //System.out.println("frist big=" + big);
                        for (int x = 0; x < 4; x++) {
                            for (int w = 0; w < 8; w++) {
                                if (pl[x].charAt(0) == na[w]) {
                                    pg = pg + pn[w];
                                }
                            }
                        }
                        for (int x = 0; x < 4; x++) {
                            if (lw == x) {
                                continue;
                            }
                            //System.out.println("bet chacking=" + bet);
                            if (istrump == true) {
                                if (pl[x].charAt(1) == bet.charAt(0) && ch != bet.charAt(0)) {
                                    //System.out.println("ch changing before=" + ch);

                                    ch = bet.charAt(0);
                                    big = pl[x];
                                    //System.out.println("ch changing after=" + ch);
                                }
                            }

                            if (pl[x].charAt(1) == ch) {
                                int ibig = -1;
                                int ipl = -1;
                                for (int y = 0; y < 8; y++) {
                                    if (na[y] == big.charAt(0)) {
                                        ibig = y;
                                    }
                                    if (na[y] == pl[x].charAt(0)) {
                                        ipl = y;
                                    }

                                }
                                if (ipl < ibig) {
                                    big = pl[x];
                                    System.out.println(" big=" + big);
                                }
                            }
                        }
                        cou = 0;
                        hands++;

                        System.out.println("final big= " + big);
                        System.out.println("total point= " + pg);
                        int p = -1;
                        for (int i = 0; i < 4; i++) {
                            if (pl[i].equalsIgnoreCase(big)) {
                                p = i;
                                lw = i;
                                point[p] = point[p] + pg;
                                break;
                            }
                        }
                       
                        p = p + 1;
                        System.out.println("player: " + p + "won");
                        for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            
                            threads[i].os.println("Lastwinner: " + String.valueOf(p));
                        }
                        
                        }

                        int mx = 0;
                        int mxind = -1;
                        for (int i = 1; i <= 4; i++) {
                            if (point[i - 1] > mx) {
                                mx = point[i - 1];
                                mxind = i;
                            }
                            System.out.println("player: " + i + "Score: " + point[i - 1]);
                        }
                        if (hands == 8) {
                            System.out.println("FINALY WINNER PLAYER : " + mxind + " SCORE: " + mx);
                        }

                    }

                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            threads[i].os.println(line);
                        }
                    }
                } else if (line.startsWith("is")) {
                    istrump = true;
                } else {
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            threads[i].os.println("<" + name + " told : " + line);

                        }
                    }

                }
            }
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("*** The user " + name
                            + " is leaving the chat room !!! ***");
                }
            }
            os.println("*** Bye " + name + " ***");

            /*
             * Clean up. Set the current thread variable to null so that a new client
             * could be accepted by the server.
             */
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

            /*
             * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            os.close();
            clientSocket.close();

        } catch (IOException e) {
        }

    }
}
