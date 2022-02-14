package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;
    private EchoClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public static EchoClient connectTo(int port){
        var localhost = "127.0.0.1";
        return new EchoClient(localhost,port);
    }
    public void run(){
        System.out.printf("Напиши 'bye' чтобы выйти%n%n%n");
        try(var socket = new Socket(host,port)){
            var sc = new Scanner(System.in,"UTF-8");
            var output = socket.getOutputStream();
            var writer = new PrintWriter(output);
            try(sc; writer){
                while(true){
                    String mess = sc.nextLine();
                    writer.write(mess);
                    writer.write(System.lineSeparator());
                    writer.flush();
                    if("bye".equalsIgnoreCase(mess)){
                        return;
                    }
                }
            }
        }catch (NoSuchElementException ex){
            System.out.println("connection dropped");
        }catch (IOException e){
            var msg = "Can't connect to %s:%s!%n";
            System.out.printf(msg,host,port);
            e.printStackTrace();
        }
    }
}