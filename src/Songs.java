import java.sql.*;
import java.util.Scanner;

/**
 * Walter Broncano
 * lab 4
 */


public class Songs {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/favoritesongs";
        String user = "firstUser";
        String password = "fuser";
        String getSongs = "SELECT * FROM Songs ";

        Scanner sc = new Scanner(System.in);
        Connection connect = DriverManager.getConnection(url, user, password);
        Statement mySql = connect.createStatement();

        String SongName = "SongName";
        String ArtistName = "ArtistName";
        String SongGenre = "SongGenre";
        String releaseYear = "ReleaseYear";
        String album = "album";

        System.out.println("Please select from below:");
        while (true){

            System.out.println("1: Show all Favorite Songs");
            System.out.println("2: Insert a new song");
            System.out.println("3: Get specific information WHERE"
                                +"\n4: exit");
            System.out.print("enter selection: ");

            int choice = sc.nextInt();



        if(choice == 1){
            ResultSet myResult = mySql.executeQuery(getSongs);

            System.out.println("Favorite Songs:");

            int songInt = 0;
            while(myResult.next()){

                songInt++;

                System.out.println("song " +songInt +":");
                System.out.println("song name: "
                        +myResult.getString(SongName)+ "\n artist: "
                        +myResult.getString(ArtistName) +"\n Genre: "
                        +myResult.getString(SongGenre) +" \nRelease Year: "
                        +myResult.getString(releaseYear) +"\n album name: "
                        +myResult.getString(album));
                System.out.println();

            }
    }
        if (choice == 2) {
            System.out.print("enter song name: ");
           String InSName = sc.next();
           System.out.print("\nenter artist name: ");
           String InArtist = sc.next();
            System.out.print("\nenter song genre: ");
            String InGenre = sc.next();
            System.out.print("\nenter release year: ");
            String InYear = sc.next();
            System.out.print("\nenter album name: ");
            String InAlbum = sc.next();

            PreparedStatement post = connect.prepareStatement("INSERT INTO Songs values (?,?,?,?,?)");
            post.setString(1,InArtist);
            post.setString(2, InSName);
            post.setString(3,InGenre);
            post.setString(4,InYear);
            post.setString(5,InAlbum);

            post.executeUpdate();
        }
        if(choice == 3){
            //Scanner sc = new Scanner(System.in);
            System.out.println("Select from Songs Where ");
            String variable = sc.next();

            System.out.print("= ");
            String where = sc.next();

            PreparedStatement GetSongData = connect.prepareStatement(getSongs +"WHERE " +variable  +"= ?");
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

        if(choice == 4){

            break;
        }
        }



    }

}

