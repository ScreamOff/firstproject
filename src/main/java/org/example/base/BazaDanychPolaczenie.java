package org.example.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/// Klasa umożliwiająca nawiązanie połączenia z bazą danych SQLite.
public class BazaDanychPolaczenie {

    /// Metoda nawiązująca połączenie z bazą danych.
    /// @return Obiekt Connection reprezentujący połączenie z bazą danych.
    public static Connection connect() {
        Connection connection = null;
        try {
            // Nawiązanie połączenia z bazą danych SQLite (plik baza.sqlite).
            connection = DriverManager.getConnection("jdbc:sqlite:baza.sqlite");

        } catch (SQLException e) {
            // W przypadku błędu wypisz informacje o błędzie.
            e.printStackTrace();
        }
        return connection;
    }

    /// Metoda główna służąca do przetestowania nawiązania połączenia z bazą danych.
    public static void main(String[] args) {
        connect();
    }
}
