package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.server.Server;

@Slf4j
public class RunServer {
    public static void main(String[] args) {
        int port = 12224;
        new Server(port, 2);
    }
}