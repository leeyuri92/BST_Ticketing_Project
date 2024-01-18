<%@ page language="java"	contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>오픈공지 페이지</title>
  <%@include file="/common/bootstrap_common.jsp" %>

  <script type="text/javascript">
    /* 자바 스크립트 부분 */

  </script>

</head>
<body>
<!--================================= header start ==================================-->
	<%@include file="/include/ticket_header.jsp" %>
<!--================================= header start ==================================-->

<!--================================= body start ==================================-->
<div class="container mt-5">
  <div class="page-header">
    <h2 class="nav justify-content-center">오픈공지</h2>
    <hr />
  </div>
</div>
<div>
  <%
  List<Map<String, Object>> listA = (List<Map<String, Object>>) request.getAttribute("listA");
  if (listA != null && !listA.isEmpty()) {
      for (Map<String, Object> item : listA) {
          out.println("Item: " + item);
      }
  } else {
      out.println("List is either null or empty.");
  }
  %></div>
<!--================================= body start ==================================-->

<!--================================= footer start ==================================-->
	<%@include file="/include/ticket_footer.jsp" %>
<!--================================== footer end ===================================-->


</body>
</html>