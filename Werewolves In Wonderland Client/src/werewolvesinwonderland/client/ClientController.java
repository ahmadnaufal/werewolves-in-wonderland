/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import werewolvesinwonderland.client.controller.GameController;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientController {
    private Socket socket;
    private DatagramSocket udpSocket;

    private String serverHostName;
    private String clientHostName;
    private int serverPort;
    private int listenPort;

    private DataOutputStream os;

    private ClientListenerTCP clientListenerTcpHandle = null;
    private ClientListenerUDP clientListenerUdpHandle = null;
    private GameController gameHandler;

    public static String lastSentMethod = "";

    /**
     * Constructor for Client Controller
     * Precondition: hostName is not an empty string, or port is defined
     * @param hostName the host name
     * @param port the port number
     * @param listenport the port for udp connection
     * @param gameController
     */
    public ClientController(String hostName, int port, int listenport, GameController gameController) {
        serverHostName = hostName;
        serverPort = port;
        listenPort = listenport;
        gameHandler = gameController;
    }

    /**
     *
     */
    public int initClientConnection() {
        try {
            InetAddress inetAddr = InetAddress.getByName(serverHostName);
            if (!inetAddr.isReachable(10000)) {
                System.err.println("Address " + serverHostName + " is unreachable.");
                return -3;
            }

            socket = new Socket(inetAddr, serverPort);
            clientListenerTcpHandle = new ClientListenerTCP(socket, this,
                    new DataOutputStream(socket.getOutputStream()));
            os = new DataOutputStream(socket.getOutputStream());

            udpSocket = new DatagramSocket(listenPort);
            clientListenerUdpHandle = new ClientListenerUDP(udpSocket, this);

            InetAddress inetAddress = InetAddress.getLocalHost();
            clientHostName = inetAddress.getHostAddress();
            return 1;
        } catch (UnknownHostException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     * @return the hostName
     */
    public String getServerHostName() {
        return serverHostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setServerHostName(String hostName) {
        this.serverHostName = hostName;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return serverPort;
    }

    /**
     * @param port the port to set
     */
    public void setServerPort(int port) {
        this.serverPort = port;
    }

    /**
     * @return the port
     */
    public int getListenPort() {
        return listenPort;
    }

    /**
     * @param listenPort the port to set
     */
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getClientHostName() {
        return clientHostName;
    }

    public DatagramSocket getUdpSocket() {
        return udpSocket;
    }

    public DataOutputStream getOutputStream() {
        return os;
    }

    /**
     * @return the gameHandler
     */
    public GameController getGameHandler() {
        return gameHandler;
    }

    /**
     * @param gameHandler the gameHandler to set
     */
    public void setGameHandler(GameController gameHandler) {
        this.gameHandler = gameHandler;
    }
    
    public static boolean isWaitingResponse() {
        return !lastSentMethod.equals("");
    }
    
    public static void setResponseHasArrived() {
        lastSentMethod = "";
    }
}
