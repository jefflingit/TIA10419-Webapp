package com.chumore.reservation.controller;

import com.chumore.reservation.model.Reservation;
import com.chumore.reservation.model.ReservationService;
import com.chumore.reservation.model.ReservationServiceImpl;
import static com.chumore.reservation.util.InputValidator.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@WebServlet("/reservation/reservation.do")
public class ReservationServlet extends HttpServlet {

    private ReservationService service;

    public void init() throws ServletException{
        service = new ReservationServiceImpl();
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        doPost(req,res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        System.out.println("action = " + action);
        String forwardPath = "/index.jsp";
        if(action == null){
            res.setContentType("text/html;charset=UTF-8");
            RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
            dispatcher.forward(req,res);
            return;
        }
        switch(action){
            case "getAll":
                forwardPath = getAllRes(req,res);
                break;
            case "getOneForDisplay":
                forwardPath = getOneResForDisplay(req,res);
                break;
            case "getOneForUpdate":
                forwardPath = getOneResForUpdate(req,res);
                break;
            case "getReservationsByCompositeQuery":
                forwardPath = getReservationsByCompositeQuery(req,res);
                break;
            case "insert":
                forwardPath = addRes(req,res);
                break;
            case "update":
                forwardPath = updateRes(req,res);
                break;
            case "delete":
                forwardPath = deleteRes(req,res);
                break;
            default:
                forwardPath = "/index.jsp";
        }

        res.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
        dispatcher.forward(req,res);

    }

    public String getOneResForDisplay(HttpServletRequest req,HttpServletResponse res){
        List<String> errorMsgs  = new LinkedList<String>();
        req.setAttribute("errorMsgs",errorMsgs);
        Integer reservationId = validateInteger(req.getParameter("reservationId"),"訂位編號不得為空白",errorMsgs);
        System.out.println(reservationId);


        if(!errorMsgs.isEmpty()){
            return "/index.jsp";
        }else{
            Reservation  reservation = service.getReservationById(reservationId);
            if(reservation == null){
                errorMsgs.add("查無資料");
                return "/index.jsp";
            }
            req.setAttribute("reservation",reservation);
            return "/back-end/reservation/listOneReservation.jsp";
        }

    }

    public String getOneResForUpdate(HttpServletRequest req,HttpServletResponse res){
        List<String> errorMsgs =  new LinkedList<String>();
        req.setAttribute("errorMsgs",errorMsgs);
        Integer reservationId = validateInteger(req.getParameter("reservationId"),"訂位編號不得為空白",errorMsgs);
        if(!errorMsgs.isEmpty()){
            return "/back-end/reservation/listAllReservation.jsp";
        }else{
            Reservation reservation  = service.getReservationById(reservationId);
            req.setAttribute("reservation",reservation);
            return "/back-end/reservation/update_reservation.jsp";
        }

    }

    public String getReservationsByCompositeQuery(HttpServletRequest req,HttpServletResponse res){
        Map<String,String[]> params = req.getParameterMap();
        Set<Map.Entry<String,String[]>> entry = params.entrySet();
        List<String> errorMsgs = new LinkedList<String>();
        req.setAttribute("errorMsgs",errorMsgs);

        for(Map.Entry<String,String[]> row: entry){
            String key = row.getKey();
            String value = row.getValue()[0];
            if(value==null||value.trim().isEmpty()){
                continue;
            }
            switch(key){
                case "memberId":
                    validateInteger(value,"消費者編號欄位請輸入數字",errorMsgs);
                    break;
                case "restId":
                    validateInteger(value,"餐廳編號欄位請輸入數字",errorMsgs);
                    break;
                case "phoneNumber":
                    validateString(value,"\\d{10}$","電話號碼格式錯誤",errorMsgs);
                    break;
                case "guestCount":
                    validateInteger(value,"人數欄位請輸入數字",errorMsgs);
                    break;
            }

        }

        if(!errorMsgs.isEmpty()){
            return "/index.jsp";
        }else{
            List<Reservation> reservations = service.getReservationsByCompositeQuery(params);
             if(reservations.isEmpty()){
               errorMsgs.add("查無資料");
               return "/index.jsp";
             }
             req.setAttribute("reservations",reservations);
             return "/back-end/reservation/listReservationByCompositeQuery.jsp";
        }


    }


    public String getAllRes(HttpServletRequest req,HttpServletResponse res){
        String page = req.getParameter("page");
        int currentPage = (page==null)? 1:Integer.parseInt(page);

        List<Reservation> reservations = service.getAll(currentPage);

        int totalPages = service.getPageTotal();
        req.setAttribute("totalPages",totalPages);

        req.setAttribute("reservations",reservations);
        req.setAttribute("currentPage",currentPage);
        return "/back-end/reservation/listAllReservation.jsp";
    }

    public String addRes(HttpServletRequest req,HttpServletResponse res){
        List<String> errorMsgs = new LinkedList<String>();
        req.setAttribute("errorMsgs",errorMsgs);
        Integer memberId = validateInteger(req.getParameter("memberId"),"會員編號請輸入數字",errorMsgs);
        Integer restId = validateInteger(req.getParameter("restId"),"餐廳編號請輸入數字",errorMsgs);
        Date reservationDate = validateDate(req.getParameter("reservationDate"),"訂位日期不得為空白",errorMsgs);
        Time reservationTime = validateTime(req.getParameter("reservationTime"),"訂位時間不得為空白",errorMsgs);
        Integer guestCount = validateInteger(req.getParameter("guestCount"),"人數請輸入數字",errorMsgs);
        String phoneNumber = validateString(req.getParameter("phoneNumber"),"\\d{10}$","電話格式錯誤",errorMsgs);
        Integer resevationStatus = 1; // 未報到

        if(!errorMsgs.isEmpty()){
            Reservation reservation = new Reservation();
            reservation.setMemberId(memberId);
            reservation.setRestId(restId);
            reservation.setReservationDate(reservationDate);
            reservation.setReservationTime(reservationTime);
            reservation.setGuestCount(guestCount);
            reservation.setPhoneNumber(phoneNumber);
            reservation.setReservationStatus(resevationStatus);
            req.setAttribute("reservation",reservation);
            return "/back-end/reservation/addReservation.jsp";
        }else{
            Reservation reservation = service.addRes(memberId,restId,reservationDate,reservationTime,guestCount,resevationStatus,phoneNumber);

            req.setAttribute("addSuccess","新增成功");
            req.setAttribute("reservation",reservation);
            return "/back-end/reservation/listOneReservation.jsp";

        }

    }


    public String updateRes(HttpServletRequest req,HttpServletResponse res){
        List<String> errorMsgs = new LinkedList<String>();
        req.setAttribute("errorMsgs",errorMsgs);
        Integer reservationId = Integer.valueOf(req.getParameter("reservationId"));
        Reservation reservation = service.getReservationById(reservationId);
        req.setAttribute("reservation",reservation);

        Integer memberId = validateInteger(req.getParameter("memberId"),"會員編號不得為空白",errorMsgs);
        Integer restId = validateInteger(req.getParameter("restId"),"餐廳編號不得為空白",errorMsgs);
        Date reservationDate = validateDate(req.getParameter("reservationDate"),"訂位日期不得為空白",errorMsgs);
        Time reservationTime = validateTime(req.getParameter("reservationTime"),"訂位時間不得為空白",errorMsgs);
        Integer guestCount = validateInteger(req.getParameter("guestCount"),"人數不得為空白",errorMsgs);
        Integer reservationStatus = reservation.getReservationStatus();
        String phoneNumber = validateString(req.getParameter("phoneNumber"),"\\d{10}$","電話不得為空白",errorMsgs);
        Timestamp createdDatetime = reservation.getCreatedDatetime();

        if(!errorMsgs.isEmpty()){
            return "/back-end/reservation/addReservation.jsp";
        }else{
            reservation = service.updateRes(reservationId,memberId,restId,reservationDate,reservationTime,guestCount,reservationStatus,phoneNumber,createdDatetime);
            req.setAttribute("reservation",reservation);
            return "/back-end/reservation/listOneReservation.jsp";

        }

    }

    public String deleteRes(HttpServletRequest req,HttpServletResponse res){
        service.deleteRes(Integer.valueOf(req.getParameter("reservationId")));

        String deleteSuccess = "成功刪除編號為"+req.getParameter("reservationId")+"的訂位紀錄";
        req.setAttribute("deleteSuccess",deleteSuccess);
        return "/back-end/reservation/listAllReservation.jsp";
    }

}
