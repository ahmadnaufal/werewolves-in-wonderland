/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.server;

import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ServerMain {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port to listen: ");
        int port = scanner.nextInt();
        
        ServerController server = new ServerController(port);
        server.initializeServer();
    }
    
}
