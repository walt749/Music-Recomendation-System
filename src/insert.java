import java.sql.*;
import java.util.Scanner;


public class insert {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/favoritesongs";
        String user = "firstUser";
        String password = "fuser";
        Connection connect = DriverManager.getConnection(url, user, password);
        Statement mySql = connect.createStatement();

        String song = "songName";
        String artist = "artista";
        String genre = "genre";
        int year = 1900;
        String album = "albums";


        PreparedStatement post = connect.prepareStatement("INSERT INTO Songs values (?,?,?,?,?)");

        post.setString(1,artist);
        post.setString(2,song);
        post.setString(3,genre);
        post.setString(4, String.valueOf(year));
        post.setString(5,album);

        /**
         * PreparedStatement post = connect.prepareStatement("INSERT INTO Songs values ('"
         *                 +artist +"', '" +song +"','" +genre +"','" +year +"','" +album +"');"
         *         );
         */

        post.executeUpdate();



    }

}
