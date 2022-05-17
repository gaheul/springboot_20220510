
const boardListTable = document.querySelector('.board-list-table');

let path = window.location.pathname; //uri

load();

 function load(){ 
	let boardCode = path.substring(path.lastIndexOf("/") + 1);//뒤에서부터 /찾음(n번째) ->/다음부터 ->자른다
	$.ajax({
		type: "get",
		url: `/board/${boardCode}`,
		dataType: "text",
		success: function(data){
			let boardObj = JSON.parse(data);
			getBoardDtl(boardObj.data);
		},
		error:function(){
			alert("비동기 처리 오류");
		}
	});
}

function getBoardDtl(data){
	boardListTable.innerHTML = `
	<tr>
		<th>제목</th>
		<td>${data.title}</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${data.username}</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>${data.boardCount}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td><pre>${data.content}</pre></td>
	</tr>
	`
}






 