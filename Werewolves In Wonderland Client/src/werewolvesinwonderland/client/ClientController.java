/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    
    private String hostName;
    private int port;
            
    private Socket mSocket;
    private DataInputStream is;
    private DataOutputStream os;
    
    /**
     * Constructor for Client Controller
     * Precondition: hostName is not an empty string, or port is defined
     * @param hostName the host name
     * @param port the port number
     */
    public ClientController(String hostName, int port) {
        setHostName(hostName);
        setPort(port);
    }
    
    /**
     * 
     */
    public void initClientConnection() {
        
        try {
            InetAddress inetAddr = InetAddress.getByName(hostName);
            if (!inetAddr.isReachable(10000)) {
                System.err.println("Address " + hostName + " is unreachable.");
                return;
            }
            
            mSocket = new Socket(hostName, port);
            
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
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the mSocket
     */
    public Socket getmSocket() {
        return mSocket;
    }

    /**
     * @param mSocket the mSocket to set
     */
    public void setmSocket(Socket mSocket) {
        this.mSocket = mSocket;
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
