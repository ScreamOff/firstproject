package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.client.Client;

@Slf4j
public class RunClient {
    public static void main(String[] args) {
        int port = 12224;
        new Client("localhost", port);
    }
}