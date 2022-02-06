import java.sql.*;
import java.util.Scanner;

//Walter Broncano

public class MusicRecomendation {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/CSC315Final2021";
        String user = "API";
        String password = "api";
        Connection connect = DriverManager.getConnection(url, user, password);
        Statement mySql = connect.createStatement();
        Scanner sc = new Scanner(System.in);

       // 4 A query to determine which sub_genres come from which regions
        String query1 = "SELECT S.sgname, O.cname, C.rname FROM Band_Styles S , Band_Origins O, Country C\n" +
                " where S.bname = O.bname and O.cname = C.cname;";

        PreparedStatement GetData4 = connect.prepareStatement(query1);

        ResultSet Res1 = GetData4.executeQuery();
        System.out.println(" Query 4: which sub_genres come from which region:");

        while (Res1.next()){
            System.out.println( "Sub Genre: "
                    +Res1.getString("sgname")
                    +", Region of Origin: "
                    +Res1.getString("rname")
            );
                    }


        // "what other bands, not currently in their favorites, " +
        //        "are of the same sub_genres as those which are";
        String query2 = "SELECT B0.bname, BS0.sgname FROM Bands B0, Band_Styles BS0 Where " +
                "B0.bname = BS0.bname and  B0.bname not in (SELECT DISTINCT B.bname FROM" +
                " FavoriteBands FB, Bands B, Band_Styles BS WHERE FB.userID ";

        String query2A = " and FB.UserFavoriteBandID != B.bid and B.bname = BS.bname and BS.sgname in" +
                "(SELECT BS1.sgname FROM FavoriteBands FB1 , Bands B1, Band_Styles BS1 WHERE FB1.userID ";
        String subquery2 = " and FB1.UserFavoriteBandID = B1.bid and B1.bname = BS1.bname));";

        PreparedStatement GetData5 = connect.prepareStatement(query2 +"= ? " + query2A +" = ? " +subquery2);
        GetData5.setString(1, "1000");
        GetData5.setString(2, "1000");

        ResultSet Res2 = GetData5.executeQuery();

        System.out.println("Query 5: Other bands not in favorites of user: 1000 are in the same sub_genres");

        while (Res2.next()){

            System.out.println( "Band Name: "
                    +Res2.getString("bname")
                    +", Sub Genre: "
                    +Res2.getString("sgname")
            );
        }

        // "what other bands, not currently in their favorites, " +
        //        "are of the same sub_genres as those which are";
        String query3 = "SELECT DISTINCT B.bname , SG.gname  FROM Bands B, " +
                "FavoriteBands FB, Band_Styles BS, Sub_Genre SG  WHERE FB.userID ";

        String query3A = "and FB.UserFavoriteBandID != B.bid and B.bname = BS.bname and BS.sgname = SG.sgname\n" +
                "                                                                        and SG.gname in\n" +
                "                                                                            (SELECT SG1.gname FROM Bands B1 , FavoriteBands FB1, Band_Styles BS1, Sub_Genre SG1\n" +
                "                                                                                    WHERE FB1.userID = 1000 AND FB1.UserFavoriteBandID = B1.bid\n" +
                "                                                                                    AND B1.bname = BS1.bname AND BS1.sgname = SG1.sgname); ";
       // String subquery2 = " and FB1.UserFavoriteBandID = B1.bid and B1.bname = BS1.bname));";

        PreparedStatement GetData6 = connect.prepareStatement(query3 +"= ? " + query3A);
        GetData6.setString(1, "1000");

        ResultSet Res3 = GetData6.executeQuery();

        System.out.println("Query 6:  a query to determine what other bands," +
                " not currently in their favorites, are of the same genres as those which are");

        while (Res3.next()){

            System.out.println( "Band Name: "
                    +Res3.getString("bname")
                    +", Genre: "
                    +Res3.getString("gname")
            );
        }


        String query4 = "SELECT U.UserName, B.bname FROM user U," +
                " FavoriteBands FB, Bands B WHERE U.userID  ";

        String query4A =  " and U.userID = FB.userID and Fb.UserFavoriteBandID = B.bid\n" +
                "        and FB.UserFavoriteBandID in\n" +
                " (SELECT FB1.UserFavoriteBandID FROM user U1, FavoriteBands FB1 WHERE U1.userID" ;

        PreparedStatement GetData7 = connect.prepareStatement(query4 +"!= ? " + query4A + " = ?" +  "and U1.userID = FB1.userID);");
        GetData7.setString(1, "1000");
        GetData7.setString(2, "1000");
        ResultSet Res4 = GetData7.executeQuery();

        System.out.println("Query 7: a query which finds other users " +
                "who have the same band in their favorites, and list their other favorite bands.");

        while (Res4.next()){

            System.out.println( "User Name: "
                    +Res4.getString("UserName")
                    +", Band: "
                    +Res4.getString("bname")
            );
        }


        String query5 = "SELECT DISTINCT B.bname, BO.cname, BS.sgname FROM FavoriteBands FB ,Bands B, Band_Origins BO, Band_Styles BS, Sub_Genre SG, user u\n" +
                "                                                                        WHERE FB.userID ";

        String query5A =  " and FB.UserFavoriteBandID = B.bid and B.bname = BO.bname and B.bname = BS.bname and u.userID = FB.userID\n" +
                "                                                                        and u.UserCountry != BO.cname\n" +
                "                                                                        order by SG.gname ;" ;

        PreparedStatement GetData8 = connect.prepareStatement(query5 +"!= ? " + query5A);
        GetData8.setString(1, "1000");
        ResultSet Res5 = GetData8.executeQuery();

        System.out.println("Query 8: a query to list other countries, excluding the user’s home country, " +
                "where they could travel to where they could hear the same genres as the bands in their favorites");

        while (Res5.next()){

            System.out.println( "User Name: "
                    +Res5.getString("cname")
                    +", Band: "
                    +Res5.getString("bname")
            );
        }


        PreparedStatement addUsers = connect.prepareStatement("INSERT INTO user values (?,?,?)");
        addUsers.setString(1,"1500");
        addUsers.setString(2,"'James Albert'");
        addUsers.setString(3,"'Canada'");
        addUsers.executeUpdate();

        PreparedStatement addUsers2 = connect.prepareStatement("INSERT INTO user values (?,?,?)");
        addUsers2.setString(1,"4500");
        addUsers2.setString(2,"'Albert Alan'");
        addUsers2.setString(3,"'United States'");
        addUsers2.executeUpdate();

        PreparedStatement addUsers3 = connect.prepareStatement("INSERT INTO user values (?,?,?)");
        addUsers3.setString(1,"2400");
        addUsers3.setString(2,"'James Mich'");
        addUsers3.setString(3,"'England'");
        addUsers3.executeUpdate();


        PreparedStatement AddBands = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands.setString(1,"1500");
        AddBands.setString(2,"4");

        AddBands.executeUpdate();
        PreparedStatement AddBands1 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands1.setString(1,"1500");
        AddBands1.setString(2,"2");

        AddBands1.executeUpdate();
        PreparedStatement AddBands2 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands2.setString(1,"1500");
        AddBands2.setString(2,"5");

        AddBands2.executeUpdate();
        PreparedStatement AddBands3 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands3.setString(1,"1500");
        AddBands3.setString(2,"7");

        AddBands3.executeUpdate();

        PreparedStatement AddBands3a = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands3a.setString(1,"1500");
        AddBands3a.setString(2,"10");

        AddBands3a.executeUpdate();

        PreparedStatement AddBands4 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands4.setString(1,"4500");
        AddBands4.setString(2,"8");

        AddBands4.executeUpdate();
        PreparedStatement AddBands5 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands5.setString(1,"4500");
        AddBands5.setString(2,"5");

        AddBands5.executeUpdate();
        PreparedStatement AddBands6 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands6.setString(1,"4500");
        AddBands6.setString(2,"2");

        AddBands6.executeUpdate();
        PreparedStatement AddBands6a = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands6a.setString(1,"4500");
        AddBands6a.setString(2,"7");

        AddBands6a.executeUpdate();



        PreparedStatement AddBands7 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands7.setString(1,"2400");
        AddBands7.setString(2,"2");

        AddBands7.executeUpdate();
        PreparedStatement AddBands8 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands8.setString(1,"2400");
        AddBands8.setString(2,"1");

        AddBands8.executeUpdate();
        PreparedStatement AddBands9 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands9.setString(1,"2400");
        AddBands9.setString(2,"4");

        AddBands9.executeUpdate();
        PreparedStatement AddBands10 = connect.prepareStatement("INSERT INTO FavoriteBands values (?,?)");
        AddBands10.setString(1,"2400");
        AddBands10.setString(2,"3");

        AddBands10.executeUpdate();

    }



}
