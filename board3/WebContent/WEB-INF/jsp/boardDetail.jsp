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
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<style>
	html, body {
		margin: 0;
		padding: 0;
		width: 100%;
		height: 100%;
	}
	
	#contentContainer {
		width: 100%;
		height: 100%;
		overflow-y: scroll;
	}
	
	#cmtModContainer {
		visibility: hidden;
		width: 100%;
		height: 100%;
		z-index: 10;
		position:absolute;
		background-color: #2c3e5090;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	#cmtModWin {	
		background-color: white;
		width: 200px;
		height: 200px;
	}
</style>
</head>
<body>
	<div id="cmtModContainer">
		<div id="cmtModWin">
			<form id="cmtFrm" action="boardCmt?mod=mod" method="post">
				<input type="hidden" name="i_board" value="${data.i_board }">
				<input type="hidden" name="i_cmt" value = "">				
				<div><textarea name="cmt"></textarea></div>
				<div><input type="submit" value="수정"></div>
			</form>
			<button onclick="closeCmtModWin()">취소</button>
		</div>
	</div>
	<div>게시판내용임!!</div>
	<div>
		게시글 PK는 : ${data.i_board }
		제목은 :${data.title }
		내용은 :${data.ctnt }
		작성자 PK는 : ${data.i_user }
		조회수는 ${data.cnt }
		로그인 유져 PK는 : ${loginUser.i_user}
	</div>
	<div>
		<a href = "boardReg"><button>글쓰기</button></a>
	</div>
	<div>
		<a href="boardList"><button>리스트로 돌아가기</button></a>
		<c:if test="${loginUser.i_user == data.i_user }">
			<a href="boardDetail?i_board=${data.i_board}&mod=mod"><button>수정</button></a>
			<a href="boardDel?i_board=${data.i_board}"><button>삭제</button></a>
		</c:if>
	</div>
	<div>
		<button onclick="doLike(${data.i_board})">
			<span id="markLike">
				<c:if test="${data.likeUser > 0 }">♥</c:if>
				<c:if test="${data.likeUser == 0 }">♡</c:if>
			</span>
		</button>
	</div>
	<div>
		${msg }
	</div>
	<div>
		조회수: ${data.cnt }
	</div>
	<div>
		${data.title }, ${data.ctnt }, ${data.r_dt }, ${data.userNm }
	</div>
	
	<div>
		<form action = "boardCmt" method="post">
			<input type = "hidden" name = "i_board" value = "${data.i_board }">
			<div>
				<textarea name = "cmt"></textarea>
			</div>
			<div>
				<input type = "submit" value = "댓글달기">
			</div>
		</form>
	</div>
	<table>
		<tr>
				<th>댓글pk번호</th>
				<th>댓글번호</th>
				<th>게시글번호</th>
				<th>댓글내용</th>
				<th>생성날자</th>
				<th>댓글작성자</th>
				<th>비고</th>
		</tr>
		<% int idx = 1; %>
		<c:forEach var="cmtitem" items="${cmtdata }">
					<tr>
						<td>${cmtitem.i_cmt }</td>
						<td><%=idx++ %></td>	
						<td>${cmtitem.i_board }</td>
						<td>${cmtitem.cmt }</td>
						<td>${cmtitem.r_dt }</td>
						<td>${cmtitem.cmtNm }</td>
						<td>
							<c:if test="${cmtitem.i_user == loginUser.i_user }">
								<a href = 'boardCmt?i_cmt=${cmtitem.i_cmt}&i_board=${cmtitem.i_board}'>삭제</a>
								<a href = '#' onclick="showCmtModWin(${cmtitem.i_cmt },'${cmtitem.cmt }')">수정</a>
							</c:if>
						</td>
					</tr>
		</c:forEach>
	</table>
	<script>
	function closeCmtModWin() {
		cmtModContainer.style.visibility = 'hidden'
	}

	
	function showCmtModWin(i_cmt, cmt) {			
		cmtModContainer.style.visibility = 'visible'			
		cmtFrm.i_cmt.value = i_cmt
		cmtFrm.cmt.value = cmt;
	}
	function doLike(i_board) {
			
			var isLike = (markLike.innerHTML.trim() == '♥') ?  1 : 0
			
			axios.get('boardLike', {
				params: {
					'i_board': i_board,
					'isLike': isLike
				}
			}).then(function(res) {
				if(res.data.result == 1) { //잘 처리 됨!!!
					/*
					if(markLike.innerHTML == '♥') {
						markLike.innerHTML = '♡' 
					} else {
						markLike.innerHTML = '♥'
					}
					*/
					markLike.innerHTML = (markLike.innerHTML.trim() == '♥') ?  '♡' : '♥'
				}
				
				/*
				if(res.data.result == 1) {
					markLike.innerHTML = '♥'
				} else {
					markLike.innerHTML = '♡'
				}
				*/
				console.log(JSON.stringify(res))
			})
		}
	</script>
</body>
</html>