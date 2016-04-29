/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ServerController {
    
    public ArrayList<ClientHandler> listClients = new ArrayList<>();
    public ServerSocket mServerSocket;
    public int mPort;
    
    private Game mGame;
    
    /**
     * Constructor for controllers
     * @param port the port for socket connections
     */
    public ServerController(int port) {
        mPort = port;
        mGame = new Game();
    }
    
    /**
     * Initialize socket connection of the server
     * to receive connections from its client
     */
    public void initializeServer() {
        try {
            mServerSocket = new ServerSocket(mPort, 0, InetAddress.getLocalHost());
            
            while (true) {
                Socket socket = mServerSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                ClientHandler temp = new ClientHandler(socket, this);
                listClients.add(temp);
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the game object
     * @return 
     */
    public Game getGame() {
        return mGame;
    }
}
