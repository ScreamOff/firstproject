import  java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;

public class WykonwacaZapytan
{
        public static ResultSet wykonajSelect(String wybyrane_zapytanie)
        {
            try{
                Connection connection = BazaDanychPolaczenie.connect();
                Statement statement = connection.createStatement();
                return statement.executeQuery(wybyrane_zapytanie);
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        public static void wykonajZapytanie(String zapytanie)
        {
            try {
                Connection connection = BazaDanychPolaczenie.connect();
                Statement statement = connection.createStatement();
                statement.execute(zapytanie);
            }
            catch (SQLException e)
            {
                throw  new RuntimeException(e.getMessage());
            }
        }
}



class main{
    public static  void main(String[] args)
    {

        try {
            WykonwacaZapytan.wykonajZapytanie("INSERT INTO Gracze(id_gracza,nazwa) VALUES(1,'gracz1')");

            ResultSet result = WykonwacaZapytan.wykonajSelect("SELECT * FROM Gracze");
            result.next();
            String nazwaGracza = result.getString("nazwa");
            System.out.println("Nazwa gracza :"+nazwaGracza);

            //WykonwacaZapytan.wykonajZapytanie("DELETE  FROM Gracze where id_gracza == 1");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}