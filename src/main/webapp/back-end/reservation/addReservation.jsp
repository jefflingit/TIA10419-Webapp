
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding ="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.chumore.reservation.model.*" %>

<% Reservation reservation = (Reservation) request.getAttribute("reservation"); %>


<html>
    <head>
        <title>add Reservation</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    </head>
    <body>
        <table id="table-title">
            <tr>
                <td><h3>新增訂位</h3>
                    <h4><a href="${pageContext.request.contextPath}/index.jsp">回首頁</a></h4>
                </td>
            </tr>
        </table>
        <p>this is add Reservation page</p>
        <%-- 錯誤列表 --%>
        <h3>資料新增：</h3>
        <c:if test="${not empty errorMsgs}">
            <span style="color: red">請修正以下錯誤：</span>
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li style="color: red">${message}</li>
                </c:forEach>
            </ul>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do">
            <table>
                <tr>
                    <td>消費者編號：</td>
                    <td><input type="text" name="memberId" value="<%= (reservation==null)? " ":reservation.getMemberId()%>" size="45"/></td>
                </tr>
                <tr>
                    <td>餐廳編號：</td>
                    <td><input type="text" name="restId" value="<%= (reservation==null)? " ": reservation.getRestId() %>" size="45"/></td>
                </tr>
                <tr>
                    <td>訂位人數：</td>
                    <td><input type="text" name="guestCount" value="<%= (reservation==null)?"": reservation.getGuestCount() %>" size="45" /></td>
                </tr>
                <tr>
                    <td>電話號碼：</td>
                    <td><input type="tel" pattern="[0-9]{10}" name="phoneNumber" required value="<%= (reservation==null)?"": reservation.getPhoneNumber() %>" size="45" /></td>
                </tr>
                <tr>
                    <td>訂位日期：</td>
                    <td><input type="date" name="reservationDate" value="<%= (reservation==null)?"": reservation.getReservationDate() %>" /></td>
                </tr>
                <tr>
                    <td>訂位時間：</td>
                    <td><input type="time" name="reservationTime" value="<%= (reservation==null)?"": reservation.getReservationTime() %>" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="送出新增"></td>
                </tr>
                <input type="hidden" name="action" value="insert">
                <input type="hidden" name="reservationStatus" value="1"/>

            </table>

        </form>




    </body>
</html>
