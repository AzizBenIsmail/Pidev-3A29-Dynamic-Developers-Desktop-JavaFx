/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.ReserverVoyage;
import Entity.voyage;
import Util.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ServiseReserVoy implements IServiseReserVoy<ReserverVoyage> {

    
    Connection cnx;

    public ServiseReserVoy() {
                cnx=MyDB.getInsatnce().getConnection();
    }

    
    @Override
    public void AjouterReserverVoyage(ReserverVoyage r) {
      try {
                String req = "insert into reservation_voyage(id,client_id,voyage_id,date_reservation,travel_class, age)"
                        +"values("+r.getId()+","+1+","+r.getVoyage().getID()+","+r.getDate_reservation()+",'"+r.getTravel_Class()+"',"+r.getAge()+")";
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("Voyage ajouter avec succ");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());        }    }


@Override
    public void ModifierReserverVoyage(ReserverVoyage r) {
try {

// String req ="UPDATE `voyage` SET `clien_id`='19',`destination`='ag',`nom_voyage`='18',`duree_voyage`='15',`date`='0000-00-00',`valabilite`='12',`image`='12',`prix`='12' WHERE id=33;";
            
            String req ="UPDATE reservation_voyage SET client_id=1,voyage_id=?,date_reservation=?,travel_class=?,age=? WHERE id=?";
            PreparedStatement ps= cnx.prepareStatement(req); //req dynamic plus securiser
           
            ps.setInt(1,r.getVoyage().getID());
           // ps.setString(1,);
            ps.setDate(2,r.getDate_reservation());
            ps.setString(3,r.getTravel_Class());
            ps.setInt(4,(int)r.getAge());
            ps.setInt(5,r.getId());
           ps.executeUpdate();
                        System.out.println("reservation_voyage Modifer avec succ");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceVoyage.class.getName()).log(Level.SEVERE, null, ex);
        }       }

    @Override
    public void SupprimerReserverVoyage(int ID) {
			try
    { 
      Statement st = cnx.createStatement();
      String req = "DELETE FROM reservation_voyage WHERE id = "+ID+"";
                st.executeUpdate(req);      
      System.out.println("La reservation_voyage avec l'id = "+ID+" a été supprimer avec succès...");
    } catch (SQLException ex) {
                System.out.println(ex.getMessage());        
              }    }

    @Override
    public List<ReserverVoyage> RecupererReserverVoyage() {
 List<ReserverVoyage> ReserverVoy = new ArrayList<>();
        try {
            String req ="select * from reservation_voyage";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
               ReserverVoyage r = new ReserverVoyage();
               r.setId(rs.getInt("id"));
               r.setClient(rs.getInt("client_id"));
              // r.setVoyage(rs.getInt("voyage_id"));
              // r.setDate_reservation(rs.getString("date_reservation"));
               r.setTravel_Class(rs.getString("travel_class"));
               r.setAge(rs.getInt("age"));
               
                String req1 ="select * from voyage where id= "+rs.getInt("voyage_id")+"";
                 Statement st1 = cnx.createStatement();
                 ResultSet rs1 = st1.executeQuery(req1);
                 while(rs1.next()) 
                 {
                voyage v = new voyage();
               v.setID(rs1.getInt("id"));
               v.setDestination(rs1.getString("destination"));
               v.setNom_voyage(rs1.getString("nom_voyage"));
               v.setDuree_voyage(rs1.getString("duree_voyage"));
               v.setValabilite(rs1.getString("valabilite"));
               v.setImage(rs1.getString("image"));
               v.setPrix(rs1.getInt("prix"));
                            r.setVoyage(v);

                 }

               ReserverVoy.add(r);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
            
return ReserverVoy;    }
    
}