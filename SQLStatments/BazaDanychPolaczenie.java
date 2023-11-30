import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BazaDanychPolaczenie {
    public static  Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            System.out.println("Połączono");

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

}
