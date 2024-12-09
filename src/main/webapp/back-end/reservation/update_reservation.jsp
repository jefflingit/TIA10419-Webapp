<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.chumore.reservation.model.Reservation" %>
<% Reservation reservation = (Reservation)request.getAttribute("reservation"); %>

<html>
<head>
    <title>update reservation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    <style>
        table#result{
            width:50%;
            text-align:center;
        }
    </style>
</head>
<body>
<table id="table-title">
    <tr>
        <td><h3>修改訂位資料</h3>
            <h4><a href="${pageContext.request.contextPath}/index.jsp">回首頁</a></h4>
        </td>
    </tr>
</table>
<p>this is update reservation page</p>
<form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do">
    <table>
        <tr>
            <td>訂位編號：</td>
            <td><b>${reservation.reservationId}</b></td>
        </tr>
        <tr>
            <td>消費者編號：</td>
            <td><input type="text" name="memberId" value="<%= reservation.getMemberId()%>" size="45"/></td>
        </tr>
        <tr>
            <td>餐廳編號：</td>
            <td><input type="text" name="restId" value="<%= reservation.getRestId() %>" size="45"/></td>
        </tr>
        <tr>
            <td>訂位人數：</td>
            <td><input type="text" name="guestCount" value="<%=reservation.getGuestCount() %>" size="45" /></td>
        </tr>
        <tr>
            <td>電話號碼：</td>
            <td><input type="tel" pattern="[0-9]{10}" name="phoneNumber" required value="<%= reservation.getPhoneNumber() %>" size="45" /></td>
        </tr>
        <tr>
            <td>訂位日期：</td>
            <td><input type="date" name="reservationDate" value="<%= reservation.getReservationDate() %>" /></td>
        </tr>
        <tr>
            <td>訂位時間：</td>
            <td><input type="time" name="reservationTime" value="<%= reservation.getReservationTime() %>" /></td>
        </tr>
        <tr>
            <td>訂位狀態：</td>
            <td>
                <select name="reservationStatus" size="1">
                    <option value="0" ${reservation.reservationStatus == 0 ? 'selected="selected"' : ''}>已取消</option>
                    <option value="1" ${reservation.reservationStatus == 1 ? 'selected="selected"' : ''}>未報到</option>
                    <option value="2" ${reservation.reservationStatus == 2 ? 'selected="selected"' : ''}>已報到</option>
                    <option value="other" ${reservation.reservationStatus != 0 && reservation.reservationStatus != 1 && reservation.reservationStatus != 2 ? 'selected="selected"' : ''}>其他</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>訂位成立時間：</td>
            <td>${reservation.createdDatetime}</td>
        </tr>
    </table>
    <br>
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="reservationId" value="${reservation.reservationId}">
    <input type="hidden" name="createdDatetime" value="${reservation.createdDatetime}">
    <input type="submit" value="送出修改">
</form>

</body>
</html>
