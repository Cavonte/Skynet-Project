package Mapper;

import Core.Room;
import IdentityMap.RoomIdentityMap;
import TDG.RoomsTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public RoomMapper(){
    }

    public static Room getData(int roomId) throws ClassNotFoundException, SQLException{

        readWriteLock.readLock().lock();

        try
        {
            Room room = RoomIdentityMap.getRoomFromMap(roomId);
            if (room != null)
            {
                return room;
            }
            else
            {
                ResultSet resultSet = RoomsTDG.find(roomId);

                if(resultSet.next())
                {
                    int roomId1 = resultSet.getInt("roomId");
                    String roomNumber1 = resultSet.getString("roomNumber");
                    String description = resultSet.getString("description");
                    int roomSize = resultSet.getInt("roomSize");

                    Room roomDB = new Room(roomId1, roomNumber1, description, roomSize);
                    RoomIdentityMap.addRoom(roomDB);

                    return roomDB;
                }
                else
                {
                    return null;
                }
            }
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    public static ArrayList<Room> getAllData() throws SQLException, ClassNotFoundException {

        readWriteLock.readLock().lock();

        try {
            ResultSet resultSet = RoomsTDG.findAll();
            ArrayList<Room> roomList = new ArrayList<Room>();

            if (resultSet == null)
                return null;
            else {

                while (resultSet.next()) {

                    int roomid1 = resultSet.getInt("roomId");
                    String roomNumber1 = resultSet.getString("roomNumber");
                    String description = resultSet.getString("description");
                    int roomSize = resultSet.getInt("roomSize");

                    roomList.add(new Room(roomid1, roomNumber1, description, roomSize));
                    RoomIdentityMap.addRoom(new Room(roomid1, roomNumber1, description, roomSize));
                }
                return roomList;
            }
        }
        finally{
            readWriteLock.readLock().unlock();
        }
    }

    public static void makeNew(int i, String rn, String d, int rs) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            Room ro = new Room(i, rn, d, rs);
            RoomIdentityMap.addRoom(ro);
            UnitOfWork.registerNew(ro);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void set(Room r, String rn, String d, int rs) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            r.setRoomNumber(rn);
            r.setDescription(d);
            r.setRoomSize(rs);
            UnitOfWork.registerDirty(r);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Room r) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomIdentityMap.delete(r);
            UnitOfWork.registerDelete(r);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void saveToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.insert(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.delete(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void updateToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.update(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }
}
