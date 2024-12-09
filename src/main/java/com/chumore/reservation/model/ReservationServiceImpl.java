package com.chumore.reservation.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservationServiceImpl implements ReservationService{
    private ReservationDAO dao;
    private static final int PAGE_MAX_RESULT = 5;

    public ReservationServiceImpl() {
        this.dao = new ReservationJDBCDAO();
    }

    @Override
    public Reservation addRes(Integer memberId, Integer restId, Date reservationDate, Time reservationTime, Integer guestCount, Integer reservationStatus, String phoneNumber) {
        Reservation res = new Reservation();
        res.setMemberId(memberId);
        res.setRestId(restId);
        res.setReservationDate(reservationDate);
        res.setReservationTime(reservationTime);
        res.setGuestCount(guestCount);
        res.setReservationStatus(reservationStatus);
        res.setPhoneNumber(phoneNumber);
        int reservationId = dao.insert(res);
        res.setReservationId(reservationId);

        return res;
    }

    @Override
    public Reservation updateRes(Integer reservationId, Integer memberId, Integer restId, Date reservationDate, Time reservationTime, Integer guestCount, Integer reservationStatus, String phoneNumber, Timestamp createdDatetime) {
        Reservation res = new Reservation();
        res.setReservationId(reservationId);
        res.setMemberId(memberId);
        res.setRestId(restId);
        res.setReservationDate(reservationDate);
        res.setReservationTime(reservationTime);
        res.setGuestCount(guestCount);
        res.setReservationStatus(reservationStatus);
        res.setPhoneNumber(phoneNumber);
        res.setCreatedDatetime(createdDatetime);
        dao.update(res);
        return res;
    }

    @Override
    public void deleteRes(Integer resId) {
        dao.delete(resId);
    }


    @Override
    public Reservation getReservationById(Integer resId) {
        return dao.getById(resId);
    }


    @Override
    public List<Reservation> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Reservation> getAll(int currentPage){
        return dao.getAll(currentPage);
    }

    public List<Reservation> getReservationsByCompositeQuery(Map<String,String[]> map){
        // 篩選要進行查詢的參數

        Map<String,String> query = new HashMap<>();
        Set<Map.Entry<String,String[]>> entry = map.entrySet();
        for(Map.Entry<String,String[]> row : entry){
            String key = row.getKey();
            //去除請求參數中的action
            if("action".equals(key)){
                continue;
            }

            //若為空值就跳過此參數
            String value = row.getValue()[0];
            if(value==null || value.isEmpty()){
                continue;
            }

            query.put(key,value);


        }

         return dao.getByCompositeQuery(query);
    }


    @Override
    public int getPageTotal() {
        long total = dao.getTotal();
        int pageQty = (int)(total%PAGE_MAX_RESULT==0? total/PAGE_MAX_RESULT:total/PAGE_MAX_RESULT+1);
        return pageQty;
    }
}
