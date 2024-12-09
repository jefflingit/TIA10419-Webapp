package com.chumore.reservation.model;

import java.util.List;
import java.util.Map;

public interface ReservationDAO {

    int insert(Reservation reservation);

    int update(Reservation reservation);

    Reservation getById(Integer id);

    List<Reservation> getAll();

    List<Reservation> getAll(int currentPage);

    List<Reservation> getByCompositeQuery(Map<String,String> map);

    void delete(Integer id);

    long getTotal();

}
