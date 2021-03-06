<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<body class="d-flex flex-column min-vh-100">
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
					<li class="nav-item"><a class="nav-link" href="#">캠핑 GO</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value='/board/list'/>">게시판</a></li>
					<!-- 기본적인 게시판 처리 부터 하고 나서 사용할 예정 -->
					<%-- <li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" 
						href="#" id="navbarDropdown" role="button" 
						data-toggle="dropdown" aria-haspopup="true" 
						aria-expanded="false">고객센터</a>
				        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
						  <a class="dropdown-item" href="#">공지사항</a>
				          <a class="dropdown-item" href="#">캠핑장 정보 수정요청</a>
				          <a class="dropdown-item" href="<c:url value='/unregistered/question'/>">미등록야영장 불법영업문의</a>
				          <a class="dropdown-item" href="#">캠핑장 공지사항</a>
				        </div>
			      	</li> --%>

					<!-- spring security 사용 할 경우-->
					<sec:authorize access="isAnonymous()">
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/member/login'/>">로그인</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/member/join'/>">회원가입</a></li>
						<li class="nav-item">
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
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
						</c:when>
						<c:otherwise>
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
						</c:otherwise>
					</c:choose> --%>
					<li class="nav-item"><a class="nav-link" href="#">사업장 등록</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<script>
		function logout_submit() {
			$("#logout_form").submit();
		}
	</script>

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