package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BazaDanychPolaczenie {
    public static  Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:baza.sqlite");

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
    connect();
    }
}
