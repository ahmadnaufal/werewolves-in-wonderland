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

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientController {
    
    private String serverHostName;
    private int serverPort;
    private int listenPort;
            
    private ClientListenerTCP clientListenerTcpHandle = null;
    private ClientListenerUDP clientListenerUdpHandle = null;
    
    private DataInputStream is;
    private DataOutputStream os;
    
    /**
     * Constructor for Client Controller
     * Precondition: hostName is not an empty string, or port is defined
     * @param hostName the host name
     * @param port the port number
     * @param listenport the port for udp connection
     */
    public ClientController(String hostName, int port, int listenport) {
        setServerHostName(hostName);
        setServerPort(port);
        setListenPort(listenport);
        initClientConnection();
    }
    
    /**
     * 
     */
    public void initClientConnection() {
        
        try {
            InetAddress inetAddr = InetAddress.getByName(serverHostName);
            if (!inetAddr.isReachable(10000)) {
                System.err.println("Address " + serverHostName + " is unreachable.");
                return;
            }
            
            Socket socket = new Socket(inetAddr, serverPort);
            clientListenerTcpHandle = new ClientListenerTCP(socket);
            
            DatagramSocket udpSocket = new DatagramSocket(listenPort);
            clientListenerUdpHandle = new ClientListenerUDP(udpSocket);
            
        } catch (UnknownHostException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the is
     */
    public DataInputStream getInputStream() {
        return is;
    }

    /**
     * @param is the is to set
     */
    public void setInputStream(DataInputStream is) {
        this.is = is;
    }

    /**
     * @return the os
     */
    public DataOutputStream getOutputStream() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOutputStream(DataOutputStream os) {
        this.os = os;
    }
   

}
