package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Core.Reservation;

/**
 * Created by dias on 2016-10-25.
 */

public class ReservationTDG {
    
    private static String databaseUsername = null;
    private static String databasePassword = null;
    
    
    public ReservationTDG(String username, String password){
        
        this.databaseUsername = username;
        this.databasePassword = password;
        
    }
    
    public static ResultSet findAll() throws ClassNotFoundException,SQLException {
        
        //ArrayList<Reservation> reservationsList = new ArrayList<>(30);

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");
        /*
        while(resultSet.next()){
            
            int resId = resultSet.getInt("reservationId");
            int roomId = resultSet.getInt("roomId");
            int studentId = resultSet.getInt("studentId");
            String weekDay = resultSet.getString("weekDay");
            String startTime = resultSet.getString("startTime");
            String endTime = resultSet.getString("endTime");
            int position = resultSet.getInt("position");
            
            reservationsList.add(new Reservation(roomId, studentId, weekDay, startTime, endTime, position));
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        */
        return resultSet;
        
    }
    
    public static ResultSet find(int reservationId) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE reservationId = " + reservationId);

        return resultSet;

        /*
        if(resultSet.next()){
            int resId = resultSet.getInt("reservationId");
            int roomId = resultSet.getInt("roomId");
            int studentId = resultSet.getInt("studentId");
            String weekDay = resultSet.getString("weekDay");
            String startTime = resultSet.getString("startTime");
            String endTime = resultSet.getString("endTime");
            int position = resultSet.getInt("position");
            
            resultSet.close();
            connection.close();
            
            Reservation reservation = new Reservation(roomId, studentId, weekDay, startTime, endTime, position);
            
            return reservation;
        }
        else{
            throw new SQLException("Error: empty result");
        }
        */
        
        
    }
    
    public static void insert(Reservation reservation) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO reservations " + "VALUES ( " + reservation.getId() + "," + reservation.getRoomid() + "," + reservation.getStudentid() + ", '" + reservation.getDay() + "','" + reservation.getStartTime() + "','" + reservation.getEndTime() + "'," + reservation.getPosition() + ")");
        
        statement.close();
        connection.close();
    }
    
    public static void update(Reservation reservation) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE reservations " + "SET weekDay = '" + reservation.getDay() + "', startTime = '" + reservation.getStartTime() + "', endTime = '" + reservation.getEndTime() + "' WHERE reservationId = " + reservation.getId());
        statement.close();
        connection.close();
    }
    
    public static void delete(Reservation reservation) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM reservations " + "WHERE reservationId = " + reservation.getId());
        statement.close();
        connection.close();
        
    }
    
}
