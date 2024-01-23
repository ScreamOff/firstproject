package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.client.Client;
import org.example.server.Server;

@Slf4j
public class ForTerminalUse {
    public static void main(String[] args) {
        log.info("asd");
        if (args.length != 1) {
            log.error("Provide one of arguments: --client or --server");
            throw new IllegalArgumentException("No mode args");
        }
        int port = 12224;

        if ("--client".equalsIgnoreCase(args[0])) {
            new Client("localhost", port);
        } else if ("--server".equalsIgnoreCase(args[0])) {
            new Server(port, 2);
        }
    }
}
