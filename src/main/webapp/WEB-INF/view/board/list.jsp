<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>

<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

	<meta name="description" content="">
	<meta name="author" content="">
	
	<title>Business Frontpage - Start Bootstrap Template</title>

	
	<!-- Custom styles for this template -->
	<link href="/css/business-frontpage.css" rel="stylesheet">

</head>

	<%@ include file="../inc/top.jsp" %>
	<div class="container">
		<table class="table table-striped">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">First</th>
		      <th scope="col">Last</th>
		      <th scope="col">Handle</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:choose>
		  	<c:when test="${ pageMaker.totalCount == 0}">
		  		<tr>
		  			<td>게시글이 존재하지 않습니다.</td>
		  		</tr>
		  	
		  	</c:when>
		  	<c:otherwise>
		  		<c:forEach items="${ list }" var="boardVO">
				    <tr class="tr">
				      <th scope="row">${ boardVO.bno }</th>
				      <td>${ boardVO.title }</td>
				      <td>${ boardVO.writer }</td>
				      <td>${ boardVO.regdate }</td>
				    </tr>		  			
		  		</c:forEach>
		  	</c:otherwise>
		  
		  </c:choose>
		  </tbody>
		</table>
	</div>
	<br>
	<c:choose>
		<c:when test="${ empty loginUser }">
			<div class="container text-right">
				<button type="button" class="btn btn-primary" onclick="login();">글쓰기</button>
			</div>
		</c:when>
		<c:otherwise>
			<div class="container text-right">
				<button type="button" class="btn btn-primary" onclick="location.href='/unregistered/write?user_id=${ loginUser.id }'">글쓰기</button>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="btn-toolbar" role="toolbar"
		aria-label="Toolbar with button groups">
		<div class="btn-group m-auto" role="group" aria-label="First group">
		<c:if test = "${ pageMaker.prev }">
			<button type="button" class="btn btn-secondary" onclick="location.href='<c:url value="/board/list?page=${ pageMaker.startPage-1 }"/>'">이전</button>
		</c:if>
		<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
			<button type="button" class="btn btn-secondary" onclick="location.href='<c:url value="/board/list?page=${ pageNum }"/>'">${ pageNum }</button>
		</c:forEach>
		<c:if test="${ pageMaker.next && pageMaker.endPage > 0 }">
			<button type="button" class="btn btn-secondary" onclick="location.href='<c:url value="/board/list?page=${ pageMaker.endPage + 1}"/>'">다음</button>
		</c:if>
		</div>
	</div>
	<br>
	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your
				Website 2020</p>
		</div>
		<!-- /.container -->
	</footer>

	<script>
		
		var contextPath = "${pageContext.request.contextPath}" == "" ? "/" : "${pageContext.request.contextPath}";

		function login() {
			alert("로그인 후 이용가능 합니다.");
			location.href= contextPath + "member/login";
		}
	
	</script>

	<!-- Bootstrap core JavaScript -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

</body>
</html>
