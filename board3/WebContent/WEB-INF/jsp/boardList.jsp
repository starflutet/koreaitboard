<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.koreait.model.BoardVO" %>
<%@ page import = "java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<style>
	.boardItem:hover{
		background-color : #bdc3c7;
		cursor: pointer;
	}
	#myCtnt{
		color : white;
		background-color : green;
	}
	#pagingContainer{
		display: flex;
		width:100%;
		height:300px;
		justify-content: center;
		align-items: center;
		font-size: 50px;
	}
	#boardContainer{
		display: flex;
		width:100%;
		height:300px;
		justify-content: center;
		align-items: center;
		font-size: 50px;
	}
</style>
</head>
<body>
	<div>게시판임!!</div>
	<div>
		${loginUser.cid }!!!
		Pk는${loginUser.i_user}
		환영합니다! ${loginUser.nm}
		<form action="boardList" method="post">
			<input type="submit" value="로그아웃">
		</form>
		<a href="myPage?typ=1"><button>비밀번호 수정</button></a>
	</div>
	<div>
		<a href = "boardReg"><button>글쓰기</button></a>
	</div>
	<div id = "boardContainer">
	<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>생성날짜</th>
				<th>이름</th>
				<th>조회수</th>
				<th>좋아요</th>
			</tr>
			<c:forEach var="item" items="${data }">
				<tr class ="boardItem" onclick="moveToDetail(${item.i_board})">
					<td id = "${(item.i_user==loginUser.i_user) ? 'myCtnt' :'' }">${item.i_board }</td>
					<td>${item.title }</td>
					<td>${item.r_dt }</td>
					<td id = "${(item.i_user==loginUser.i_user) ? 'myCtnt' :'' }">${item.userNm }</td>
					<td>${item.cnt }</td>
					<td>
						<c:if test="${item.likeUser > 0 }">♥</c:if>
						<c:if test="${item.likeUser == 0 }">♡</c:if>
					</td>
				</tr>
			</c:forEach>
			<!--<c:forEach var = "name" items = "${data }">
				<tr class ="boardItem" onclick="moveToDetail(${name.i_board})">
					<td id = "${(name.i_user==loginUser.i_user) ? 'myCtnt' :'' }"><c:out value = "${name.i_board }"/></td>
					<td><c:out value = "${name.title }"/></td>
					<td><c:out value = "${name.r_dt }"/></td>
					<td  id = "${(name.i_user==loginUser.i_user) ? 'myCtnt' :'' }"><c:out value = "${name.userNm }"/></td>
				</tr>
			</c:forEach>-->
	</table>
	</div>
	<div id = "pagingContainer">
		<div>
		<c:forEach begin="1" end="${totalPageCnt }" var = "i">
			<a href="boardList?page=${i }"><span style = "margin-right: 5px;">${i }</span></a>
		</c:forEach>
		</div>
	</div>
	<script>
		function moveToDetail(i_board){
			location.href = 'boardDetail?i_board='+i_board
		}
	</script>
</body>
</html>