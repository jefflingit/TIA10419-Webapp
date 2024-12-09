package com.chumore.reservation.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ReservationService {
   Reservation addRes(Integer memberId, Integer restId, Date reservationDate, Time reservationTime,Integer guestCount,Integer reservationStatus,String phoneNumber);
   Reservation updateRes(Integer reservationId, Integer memberId, Integer restId, Date reservationDate, Time reservationTime, Integer guestCount, Integer reservationStatus, String phoneNumber, Timestamp createdDatetime);
   Reservation getReservationById(Integer resId);
   void deleteRes(Integer resId);
   List<Reservation> getAll();
   List<Reservation> getAll(int currentPage);
   List<Reservation> getReservationsByCompositeQuery(Map<String,String[]> map);
   int getPageTotal();


}
