package zad1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database extends JFrame {
    private String url;
    private TravelData travelData;
    List<Offer> allOffers = new ArrayList<>();

    public Database(String url, TravelData travelData) {
        this.travelData = travelData;
        this.url = url;
    }

    public void create(){
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(
                    "insert into Offer (loc, country, depDate, arrDate, place, price, currencyCode) values (?,?,?,?,?,?,?)");
            List<String> data = travelData.getOffersDescriptions();
            for(String element: data){
                String [] tab = element.split("\t");
                ps.setString(1, tab[0]);
                ps.setString(2, tab[1]);
                ps.setDate(3, Date.valueOf(tab[2]));
                ps.setDate(4, Date.valueOf(tab[3]));
                ps.setString(5, tab[4]);
                ps.setDouble(6, Double.parseDouble(tab[5]));
                ps.setString(7, tab[6]);
                ps.execute();
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private List<Offer> getAllOffers(){
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection conn = DriverManager.getConnection(url);
            String query = "SELECT * FROM offer";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("offerId");
                String firstName = rs.getString("loc");
                String lastName = rs.getString("country");
                Date depDate = rs.getDate("depDate");
                Date arrDate = rs.getDate("arrDate");
                String place = rs.getString("place");
                float price = rs.getFloat("price");
                String currencyCode = rs.getString("currencyCode");

                Offer offer = new Offer(id, firstName, lastName, LocalDate.parse(depDate.toString()), LocalDate.parse(arrDate.toString()), place, price, currencyCode);
                allOffers.add(offer);
            }

            st.close();
            return allOffers;
        } catch (SQLException e) {
            System.out.println("problem");
        }
        return null;
    }

    public void showGui() {
       SwingUtilities.invokeLater(()->new GUI(getAllOffers()));
    }
}
