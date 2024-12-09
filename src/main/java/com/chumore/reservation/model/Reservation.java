package com.chumore.reservation.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="reservation")
public class Reservation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id",updatable=false,nullable=false)
    private Integer reservationId;

    @Column(name="member_id",nullable=false)
    private Integer memberId;

    @Column(name="rest_id",nullable=false)
    private Integer restId;

    @Column(name="guest_count",nullable=false)
    private Integer guestCount;

    @Column(name="reservation_status",nullable=false)
    private Integer reservationStatus;

    @Column(name="phone_number",nullable=false)
    private String phoneNumber;

    @Column(name="reservation_date",nullable=false)
    private Date reservationDate;

    @Column(name="reservation_time",nullable=false)
    private Time reservationTime;

    @Column(name="created_datetime",nullable=false)
    private Timestamp createdDatetime;

    public Reservation() {

    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRestId() {
        return restId;
    }

    public void setRestId(Integer restId) {
        this.restId = restId;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public Integer getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Integer reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Time reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Override
    public String toString() {
        return String.format("Reservation [reservationId=%s, memberId=%s, restId=%s, guestCount=%s, reservationStatus=%s, phoneNumber=%s, reservationDate=%s, reservationTime=%s, createdDatetime=%s]",getReservationId(),getMemberId(),getRestId(),getGuestCount(),getReservationStatus(),getPhoneNumber(),getReservationDate(),getReservationTime(),getCreatedDatetime());
    }
}
