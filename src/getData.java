import java.sql.*;
import java.util.Scanner;

public class getData {

    public static void main(String[] args) throws SQLException {

        String SongName = "SongName";
        String ArtistName = "ArtistName";
        String SongGenre = "SongGenre";
        String releaseYear = "ReleaseYear";
        String album = "album";
        String url = "jdbc:mysql://localhost:3306/favoritesongs";
        String user = "firstUser";
        String password = "fuser";
        String getSongs = "SELECT * FROM Songs ";
        Scanner sc = new Scanner(System.in);

        Connection connect = DriverManager.getConnection(url, user, password);
        Statement mySql = connect.createStatement();

        System.out.println("Select from Songs Where ");
        String variable = sc.next();

        System.out.println("equals ");
        String where = sc.next();

        System.out.println("variable: " +variable +" where: "+where);
        PreparedStatement GetSongData = connect.prepareStatement(getSongs +"WHERE " +variable+" = ?");
        GetSongData.setString(1,where);


        ResultSet VarRes = GetSongData.executeQuery();
        int songInt = 0;
        while(VarRes.next()){

            songInt++;

            System.out.println("song " +songInt +":");
            System.out.println("song name: "
                    +VarRes.getString(SongName)+ "\n artist: "
                    +VarRes.getString(ArtistName) +"\n Genre: "
                    +VarRes.getString(SongGenre) +" \nRelease Year: "
                    +VarRes.getString(releaseYear) +"\n album name: "
                    +VarRes.getString(album));
            System.out.println();

        }


    }

}
