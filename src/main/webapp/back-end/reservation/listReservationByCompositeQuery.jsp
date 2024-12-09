<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>composite query</title>
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
        <td><h3>訂位列表</h3>
            <h4><a href="${pageContext.request.contextPath}/index.jsp">回首頁</a></h4>
        </td>
    </tr>
</table>
<br>

<table id="result">
    <tr>
        <th>訂位編號</th>
        <th>餐廳編號</th>
        <th>訂位日期</th>
        <th>訂位時間</th>
        <th>人數</th>
        <th>消費者編號</th>
        <th>電話號碼</th>
        <th>訂位狀態</th>
        <th>訂位成立時間</th>
        <th>修改</th>
        <th>刪除</th>
    </tr>
    <c:forEach var="reservation" items="${reservations}">
        <tr>
            <td>${reservation.reservationId}</td>
            <td>${reservation.restId}</td>
            <td>${reservation.reservationDate}</td>
            <td>${reservation.reservationTime}</td>
            <td>${reservation.guestCount}</td>
            <td>${reservation.memberId}</td>
            <td>${reservation.phoneNumber}</td>
            <td>
                <c:choose>
                    <c:when test="${reservation.reservationStatus == 0}">
                        已取消
                    </c:when>
                    <c:when test="${reservation.reservationStatus == 1}">
                        未報到
                    </c:when>
                    <c:when test="${reservation.reservationStatus == 2}">
                        已報到
                    </c:when>
                    <c:otherwise>
                        狀態未知
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${reservation.createdDatetime}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do" style="margin-bottom: 0px;">
                    <input type="submit" value="修改">
                    <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                    <input type="hidden" name="action" value="getOneForUpdate">
                </form>
            </td>
            <td>
                <form post="post" action="${pageContext.request.contextPath}/reservation/reservation.do" style="margin-bottom: 0px;">
                    <input type="submit" value="刪除">
                    <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                    <input type="hidden" name="action" value="delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="${pageContext.request.contextPath}/index.jsp">回首頁</a></p>
</body>
</html>
