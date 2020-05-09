<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="service.SalaryDao"%>
<%@page import = "entity.Salary" %>
<%@page import="service_imp.SalaryDaoImp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    This is my JSP page. <br>
    <%
    
    	SalaryDao salarydao = new SalaryDaoImp();
    	 List<Salary> salarys = salarydao.querySalaryByTimeAndId("2020042811", "2020042811", "15643084346", "1");
    	 System.out.println(salarys.size());
     %>
  </body>
</html>
