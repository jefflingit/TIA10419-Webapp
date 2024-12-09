import com.chumore.reservation.model.Reservation;
import com.chumore.reservation.model.ReservationDAO;
import com.chumore.reservation.model.ReservationJDBCDAO;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chumore.reservation.util.InputValidator.*;

public class Tester {
    public static void main(String[] args){
        ReservationDAO dao = new ReservationJDBCDAO();
        Reservation reservation = testQueryById(23,dao);
        System.out.println(reservation);
        List<Reservation> list = testQueryAll(dao);
        for(Reservation res : list){
            System.out.println(res);
        }

        long count = testGetCount(dao);
        System.out.println(count);

        validateTest();

        testCompositeQuery(dao);
        testInsert(dao);
    }

    public static void testInsert(ReservationDAO dao){
        Reservation res = new Reservation();
        res.setMemberId(1010);
        res.setRestId(2010);
        res.setGuestCount(5);
        res.setReservationStatus(1);
        res.setReservationDate(Date.valueOf("2024-12-3"));
        res.setReservationTime(Time.valueOf("17:00:00"));
        res.setPhoneNumber("0912345670");
        int id = dao.insert(res);
        System.out.println(id);
    }

    public static Reservation testQueryById(int id,ReservationDAO dao){
        return dao.getById(id);
    }

    public static List<Reservation> testQueryAll(ReservationDAO dao){
        return dao.getAll();
    }

    public static long testGetCount(ReservationDAO dao){
        return dao.getTotal();
    }

    public static void validateTest(){
        List<String> errorMsgs = new LinkedList<String>();

        String reservationId = "";

        System.out.println(validateInteger(reservationId,"餐廳ID格式錯誤",errorMsgs));


        String guestCount = "2";
        System.out.println(validateInteger(guestCount,"人數欄位請輸入數字",errorMsgs));

        String reservationDate="2024-12-3";
        Date d =validateDate(reservationDate,"請輸入日期",errorMsgs);
        System.out.println(d);

        String reservationTime ="11:53:00";
        System.out.println(validateTime(reservationTime,"請輸入時間",errorMsgs));

        String phoneNumber = "0912345627";
        System.out.println(validateString(phoneNumber,"\\d{10}$","電話號碼不符合格式",errorMsgs));


        for(String errorMsg : errorMsgs){
            System.out.println(errorMsg);
        }

        System.out.println(errorMsgs.isEmpty());






    }

    public static void testCompositeQuery(ReservationDAO dao){
        Map<String,String> map = new HashMap();
        map.put("memberId","1002");
        map.put("startDate","2024-12-3");
        map.put("endDate","2024-12-5");
        map.put("startTime","11:00:00");
        map.put("endTime","23:00:00");
        map.put("reservationStatus","1");

        List<Reservation> reservations = dao.getByCompositeQuery(map);

        for(Reservation res : reservations){
            System.out.println(res);
        }

    }

    public static void testDelete(ReservationDAO dao){
        dao.delete(21);
    }


}
