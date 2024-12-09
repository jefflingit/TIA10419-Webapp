package com.chumore.reservation.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chumore.reservation.util.Constants.PAGE_MAX_RESULT;

public class ReservationJDBCDAO implements ReservationDAO{
    // connection 設定
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/chumore_test?serverTimezone=Asia/Taipei";
    private String user = "root";
    private String password ="123456";

    // SQL指令
    private static final String INSERT_STMT = "INSERT INTO reservation(member_id,rest_id,reservation_date,reservation_time,guest_count,reservation_status,phone_number) VALUES (?,?,?,TIME_FORMAT(?,'%H:%i'),?,?,?)";
    private static final String UPDATE_STMT = "UPDATE reservation SET member_id = ?,rest_id = ?,reservation_date = ?,reservation_time = ?,guest_count = ?,reservation_status = ?,phone_number = ?,created_datetime=? WHERE reservation_id = ?";
    private static final String GET_ONE_STMT = "SELECT * FROM reservation WHERE reservation_id = ?";
    private static final String GET_ALL_STMT = "SELECT * FROM reservation";
    private static final String GET_CURRENT_PAGE_RESULT_STMT = "SELECT * FROM reservation LIMIT ? OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(*) AS count FROM reservation";
    private static final String DELETE_STMT = "DELETE FROM reservation WHERE reservation_id = ?";
    private static final String COMPOSITE_QUERY_TEMPLATE ="SELECT * FROM reservation WHERE 1=1";
    //

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;


    @Override
    public int insert(Reservation res) {


        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(INSERT_STMT,Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1,res.getMemberId());
            pstmt.setInt(2,res.getRestId());
            pstmt.setDate(3,res.getReservationDate());
            pstmt.setTime(4,res.getReservationTime());
            pstmt.setInt(5,res.getGuestCount());
            pstmt.setInt(6,res.getReservationStatus());
            pstmt.setString(7,res.getPhoneNumber());

            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();


            int reservationId = -1;
            if(generatedKeys.next()){
                reservationId = generatedKeys.getInt(1);
            }

            return reservationId;

        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException e){
                    e.printStackTrace(System.err);
                }
            }

        }
    }

    @Override
    public int update(Reservation reservation) {

        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(UPDATE_STMT);

            pstmt.setInt(1,reservation.getMemberId());
            pstmt.setInt(2,reservation.getRestId());
            pstmt.setDate(3,reservation.getReservationDate());
            pstmt.setTime(4,reservation.getReservationTime());
            pstmt.setInt(5,reservation.getGuestCount());
            pstmt.setInt(6,reservation.getReservationStatus());
            pstmt.setString(7,reservation.getPhoneNumber());
            pstmt.setTimestamp(8,reservation.getCreatedDatetime());
            pstmt.setInt(9,reservation.getReservationId());


            pstmt.executeUpdate();
            return reservation.getRestId();

        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }


    }

    public void delete(Integer reservationId)  {
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(DELETE_STMT);
            pstmt.setInt(1,reservationId);
            pstmt.executeUpdate();

        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }
    }

    @Override
    public Reservation getById(Integer id) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setInt(1,id);

            rs = pstmt.executeQuery();


            Reservation res = null;

            while(rs.next()){
                res = new Reservation();
                res.setReservationId(rs.getInt("reservation_id"));
                res.setMemberId(rs.getInt("member_id"));
                res.setRestId(rs.getInt("rest_id"));
                res.setReservationStatus(rs.getInt("reservation_status"));
                res.setReservationDate(rs.getDate("reservation_date"));
                res.setReservationTime(rs.getTime("reservation_time"));
                res.setGuestCount(rs.getInt("guest_count"));
                res.setPhoneNumber(rs.getString("phone_number"));
                res.setCreatedDatetime(rs.getTimestamp("created_datetime"));
            }

            return res;



        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations= new ArrayList<Reservation>();
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();



            while(rs.next()){
                Reservation res = new Reservation();
                res.setReservationId(rs.getInt("reservation_id"));
                res.setMemberId(rs.getInt("member_id"));
                res.setRestId(rs.getInt("rest_id"));
                res.setReservationStatus(rs.getInt("reservation_status"));
                res.setReservationDate(rs.getDate("reservation_date"));
                res.setReservationTime(rs.getTime("reservation_time"));
                res.setGuestCount(rs.getInt("guest_count"));
                res.setPhoneNumber(rs.getString("phone_number"));
                res.setCreatedDatetime(rs.getTimestamp("created_datetime"));
                reservations.add(res);
            }


            return reservations;

        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }

    }

    @Override
    public List<Reservation> getAll(int currentPage){
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<Reservation>();
        int first =(currentPage==1)?0:(currentPage - 1) * PAGE_MAX_RESULT;

        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(GET_CURRENT_PAGE_RESULT_STMT);
            pstmt.setInt(1,PAGE_MAX_RESULT);
            pstmt.setInt(2,first);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Reservation res = new Reservation();
                res.setReservationId(rs.getInt("reservation_id"));
                res.setMemberId(rs.getInt("member_id"));
                res.setRestId(rs.getInt("rest_id"));
                res.setReservationStatus(rs.getInt("reservation_status"));
                res.setReservationDate(rs.getDate("reservation_date"));
                res.setReservationTime(rs.getTime("reservation_time"));
                res.setGuestCount(rs.getInt("guest_count"));
                res.setPhoneNumber(rs.getString("phone_number"));
                res.setCreatedDatetime(rs.getTimestamp("created_datetime"));
                reservations.add(res);
            }
            return reservations;
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }



    }

    @Override
    public List<Reservation> getByCompositeQuery(Map<String,String> map){
        List<Reservation> reservations = new ArrayList<Reservation>();
        StringBuilder sql = new StringBuilder(COMPOSITE_QUERY_TEMPLATE);
        List<Object> params = new ArrayList<>();
        ResultSet rs = null;

        if(map.containsKey("startDate") && map.containsKey("endDate")){
            sql.append(" AND reservation_date BETWEEN ? AND ?");
            params.add(Date.valueOf(map.get("startDate")));
            params.add(Date.valueOf(map.get("endDate")));

        }else if(map.containsKey("startDate")){
            sql.append(" AND reservation_date >=?");
            params.add(Date.valueOf(map.get("startDate")));

        }else if(map.containsKey("endDate")){
            sql.append(" AND reservation_date <= ?");
            params.add(Date.valueOf(map.get("endDate")));

        }

        if(map.containsKey("startTime") && map.containsKey("endTime")){
            sql.append(" AND reservation_time BETWEEN ? AND ?");
            params.add(Time.valueOf(map.get("startTime")+":00"));
            params.add(Time.valueOf(map.get("endTime")+":00"));

        }else if(map.containsKey("startTime")){
            sql.append(" AND reservation_time >=?");
            params.add(Time.valueOf(map.get("startTime")+":00"));

        }else if(map.containsKey("endTime")) {
            sql.append(" AND reservation_time <= ?");
            params.add(Time.valueOf(map.get("endTime")+":00"));
        }

        if(map.containsKey("memberId")){
            sql.append(" AND member_id = ?");
            params.add(Integer.valueOf(map.get("memberId")));
        }

        if(map.containsKey("restId")){
            sql.append(" AND rest_id = ?");
            params.add(Integer.valueOf(map.get("restId")));
        }

        if(map.containsKey("guestCount")){
            sql.append(" AND guest_count = ?");
            params.add(Integer.valueOf(map.get("guestCount")));
        }

        if(map.containsKey("phoneNumber")){
            sql.append(" AND phone_number = ?");
            params.add(map.get("phoneNumber"));
        }

        DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); //將iso 8601格式轉換成SQL可接受的格式

        if(map.containsKey("startCreatedDatetime") && map.containsKey("endCreatedDatetime")){
            sql.append(" AND created_datetime BETWEEN ? AND ?");
            LocalDateTime startCreatedDatetime = LocalDateTime.parse(map.get("startCreatedDatetime"), isoFormatter);
            LocalDateTime endCreatedDatetime = LocalDateTime.parse(map.get("endCreatedDatetime"), isoFormatter);
            params.add(Timestamp.valueOf(startCreatedDatetime));
            params.add(Timestamp.valueOf(endCreatedDatetime));
        }else if(map.containsKey("startCreatedDatetime")){
            sql.append(" AND created_datetime >= ?");
            LocalDateTime startCreatedDatetime = LocalDateTime.parse(map.get("startCreatedDatetime"), isoFormatter);
            params.add(Timestamp.valueOf(startCreatedDatetime));
        }else if(map.containsKey("endCreatedDatetime")){
            sql.append(" AND created_datetime <= ?");
            LocalDateTime endCreatedDatetime = LocalDateTime.parse(map.get("endCreatedDatetime"), isoFormatter);
            params.add(Timestamp.valueOf(endCreatedDatetime));
        }

        if(map.containsKey("reservationStatus")){
            sql.append(" AND reservation_status = ?");
            params.add(Integer.valueOf(map.get("reservationStatus")));
        }

        sql.append(" ORDER BY reservation_id ASC");

        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(sql.toString());
            for(int i = 0;i<params.size();i++){
                pstmt.setObject(i+1,params.get(i));
            }
            rs=pstmt.executeQuery();
            while(rs.next()){
                Reservation reservation = new Reservation();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setMemberId(rs.getInt("member_id"));
                reservation.setRestId(rs.getInt("rest_id"));
                reservation.setGuestCount(rs.getInt("guest_count"));
                reservation.setReservationDate(rs.getDate("reservation_date"));
                reservation.setReservationTime(rs.getTime("reservation_time"));
                reservation.setCreatedDatetime(rs.getTimestamp("created_datetime"));
                reservation.setPhoneNumber(rs.getString("phone_number"));
                reservation.setReservationStatus(rs.getInt("reservation_status"));
                reservations.add(reservation);
            }

            return reservations;


        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }


    }


    @Override
    public long getTotal() {
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            pstmt = con.prepareStatement(GET_COUNT);
            rs = pstmt.executeQuery();
            long count = -1L;
            while(rs.next()) {
                count = rs.getLong("count");
            }
            return count;
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        }catch(SQLException se){
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace(System.err);
                }
            }

        }

    }
}
