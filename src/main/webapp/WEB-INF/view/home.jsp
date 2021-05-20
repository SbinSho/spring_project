<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>

<head>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<meta name="description" content="">
<meta name="author" content="">

<title>Business Frontpage - Start Bootstrap Template</title>


<!-- Custom styles for this template -->
<link href="/css/business-frontpage.css" rel="stylesheet">

</head>

<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value='/'/>">GoCamping</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a class="nav-link"
						href="<c:url value='/'/>">캠핑 GO</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value='/board/list'/>">게시판</a></li>
					<!-- 기본적인 게시판 처리 부터 하고 나서 사용할 예정 -->
					<%-- <li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">고객센터</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#">공지사항</a> <a
								class="dropdown-item" href="#">캠핑장 정보 수정요청</a> <a
								class="dropdown-item"
								href="<c:url value='/unregistered/question'/>">미등록야영장 불법영업문의</a>
							<a class="dropdown-item" href="#">캠핑장 공지사항</a>
						</div></li> --%>

					<!-- spring security 사용 할 경우-->
					<sec:authorize access="isAnonymous()">
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/member/login'/>">로그인</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/member/join'/>">회원가입</a></li>
						<li class="nav-item">
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_MEMBER')">
						<li class="nav-item"></li>
						<li class="nav-item">
							<form action="/member/logout" id="logout_form" method="POST">
								<a class="nav-link" onclick="logout_submit()"
									style="cursor: pointer;"> <span>로그아웃</span>
								</a> <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/member/edit/info?user_id=${ loginUser.id }'/>">회원정보수정</a>
						</li>
					</sec:authorize>

					<!-- spring security 사용하지 않을 경우 -->
					<%-- <c:choose>
						<c:when test="${ empty loginUser }">
							<li class="nav-item"><a class="nav-link"
								href="<c:url value='/member/login'/>">로그인</a></li>
							<li class="nav-item"><a class="nav-link"
								href="<c:url value='/member/join'/>">회원가입</a></li>
							<li class="nav-item">
								<form action="/member/logout" id="logout_form" method="POST">
									<a class="nav-link" onclick="logout_submit()"
										style="cursor: pointer;"> <span>로그아웃</span>
									</a> <input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</form>
							</li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link"
								href="<c:url value='/member/logout?user_id=${ loginUser.id }'/>">
									<span>로그아웃</span>
							</a></li>
							<li class="nav-item"><a class="nav-link"
								href="<c:url value='/member/edit/info?user_id=${ loginUser.id }'/>">회원정보수정</a>
							</li>
						</c:otherwise>
					</c:choose> --%>
					<li class="nav-item"><a class="nav-link" href="#">사업장 등록</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Header -->
	<header class="bg-primary py-5 mb-5">
		<div class="container h-100">
			<div class="row h-100 align-items-center">
				<div class="col-lg-12">
					<h1 class="display-4 text-white mt-5 mb-2">Business Name or
						Tagline</h1>
					<p class="lead mb-5 text-white-50">Lorem ipsum dolor sit amet,
						consectetur adipisicing elit. Non possimus ab labore provident
						mollitia. Id assumenda voluptate earum corporis facere quibusdam
						quisquam iste ipsa cumque unde nisi, totam quas ipsam.</p>
				</div>
			</div>
		</div>
	</header>

	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-md-8 mb-5">
				<h2>What We Do</h2>
				<hr>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. A
					deserunt neque tempore recusandae animi soluta quasi? Asperiores
					rem dolore eaque vel, porro, soluta unde debitis aliquam
					laboriosam. Repellat explicabo, maiores!</p>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
					Omnis optio neque consectetur consequatur magni in nisi, natus
					beatae quidem quam odit commodi ducimus totam eum, alias, adipisci
					nesciunt voluptate. Voluptatum.</p>
				<a class="btn btn-primary btn-lg" href="#">Call to Action&raquo;</a>
			</div>
			<div class="col-md-4 mb-5">
				<h2>Contact Us</h2>
				<hr>
				<address>
					<strong>Start Bootstrap</strong> <br>3481 Melrose Place <br>Beverly
					Hills, CA 90210 <br>
				</address>
				<address>
					<abbr title="Phone">P:</abbr> (123) 456-7890 <br> <abbr
						title="Email">E:</abbr> <a href="mailto:#">name@example.com</a>
				</address>
			</div>
		</div>
		<!-- /.row -->

		<div class="row">
			<div class="col-md-4 mb-5">
				<div class="card h-100">
					<img class="card-img-top" src="https://placehold.it/300x200" alt="">
					<div class="card-body">
						<h4 class="card-title">Card title</h4>
						<p class="card-text">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit. Sapiente esse necessitatibus neque sequi
							doloribus.</p>
					</div>
					<div class="card-footer">
						<a href="#" class="btn btn-primary">Find Out More!</a>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-5">
				<div class="card h-100">
					<img class="card-img-top" src="https://placehold.it/300x200" alt="">
					<div class="card-body">
						<h4 class="card-title">Card title</h4>
						<p class="card-text">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit. Sapiente esse necessitatibus neque sequi
							doloribus totam ut praesentium aut.</p>
					</div>
					<div class="card-footer">
						<a href="#" class="btn btn-primary">Find Out More!</a>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-5">
				<div class="card h-100">
					<img class="card-img-top" src="https://placehold.it/300x200" alt="">
					<div class="card-body">
						<h4 class="card-title">Card title</h4>
						<p class="card-text">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit. Sapiente esse necessitatibus neque.</p>
					</div>
					<div class="card-footer">
						<a href="#" class="btn btn-primary">Find Out More!</a>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your
				Website 2020</p>
		</div>
		<!-- /.container -->
	</footer>

	<script>
		var result = "${result}";
		if (result == "error") {
			alert("오류 발생!");
		}

		function logout_submit() {
			$("#logout_form").submit();
		}
	</script>

	<!-- Bootstrap core JavaScript -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

</body>

</html>
