<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding ="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>訂位管理系統</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    </head>
    <body>
      <table id="table-title">
          <tr>
              <td><h3>Index</h3></td>
          </tr>
      </table>
      <p>This is index page</p>
      <h3>資料查詢：</h3>
      <%-- 錯誤列表 --%>
      <c:if test="${not empty errorMsgs}">
          <span style="color: red">請修正以下錯誤：</span>
          <ul>
              <c:forEach var="message" items="${errorMsgs}">
                  <li style="color: red">${message}</li>
              </c:forEach>
          </ul>
      </c:if>

      <p>查詢所有訂位：<a href="${pageContext.request.contextPath}/reservation/reservation.do?action=getAll">查詢</a></p>

      <ul>
          <li>
              <form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do">
                  <b>請輸入訂位編號：</b><input type="text" name="reservationId">
                  <input type="hidden" name="action" value="getOneForDisplay">
                  <input type="submit" value="送出">
              </form>
          </li>

          <jsp:useBean id="resService" scope="page" class="com.chumore.reservation.model.ReservationServiceImpl" />

          <li>
              <form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do">
                  <b>選擇訂位編號：</b>
                  <select name="reservationId" size="1">
                      <c:forEach var="reservation" items="${resService.all}">
                          <option value="${reservation.reservationId}">${reservation.reservationId}
                      </c:forEach>
                  </select>
                  <input type="hidden" name="action" value="getOneForDisplay">
                  <input type="submit" value="送出">
              </form>
          </li>
      </ul>

      <h3><b>複合查詢</b></h3>
      <form method="post" action="${pageContext.request.contextPath}/reservation/reservation.do">
          <ul>
              <li><span><b>以消費者ID查詢：</b></span><input type="text" name="reservationId"></li>
              <li><span><b>以餐廳ID查詢：</b></span><input type="text" name="restId"></li>
              <li><span><b>以人數查詢：</b></span><input type="text" name="guestCount"></li>
              <li><span><b>以電話號碼查詢：</b></span><input type="text" name="phoneNumber"></li>
              <li><span><b>以訂位狀態查詢：</b></span>
                  <select name="reservationStatus">
                      <option value="">選取狀態</option>
                      <option value="0">已取消</option>
                      <option value="1">未報到</option>
                      <option value="2">已報到</option>
                  </select>
              </li>
              <li><span><b>以訂位日期查詢</b></span><input type="date" name="startDate">~<input type="date" name="endDate"></li>
              <li><span><b>以訂位時間查詢</b></span><input type="time" name="startTime">~<input type="date" name="endTime"></li>
              <li><span><b>以訂位成立時間查詢</b></span><input type="datetime-local" name="startCreatedDatetime">~<input type="datetime-local" name="endCreatedDatetime"></li>
              <div>
                  <input type="hidden" name="action" value="getReservationsByCompositeQuery">
                  <input type="submit" value="送出">
              </div>
          </ul>

          <h3>新增訂位</h3>
          <p>新增訂位：<a href="${pageContext.request.contextPath}/back-end/reservation/addReservation.jsp">新增</a></p>

      </form>
    </body>
</html>
