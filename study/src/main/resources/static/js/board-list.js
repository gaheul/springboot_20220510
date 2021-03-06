/**
 * 
 */
 
 const boardListTable = document.querySelector('.board-list-table');
 const boardListPage = document.querySelector('.board-list-page');
 const pageButton = boardListPage.querySelectorAll('div');

 

 
 let nowPage = 1;
 
 load(nowPage);
 
 function load(page){
	$.ajax({//서버에서 데이터 들고오기 위함
		type: "get",
		url: "/board/list",
		data: {
			"page" : page
		},
		dataType: "text",
		success: function(data){
			let boardList = JSON.parse(data);
			getBoardList(boardList.data);
			
		},
		error: function(){
			alert("비동기 처리 오류");
		}
	});
}
 
 
 function getBoardList(data){
	while(boardListTable.hasChildNodes()){//자식노드가 있으며 반복
		boardListTable.removeChild(boardListTable.firstChild);
	}

	let tableStr= `
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
	</tr>
	`
	for(let i=0; i<data.length; i++){
		tableStr += `
			<tr class="board-items">
				<td>${data[i].boardCode}</td>
				<td>${data[i].title}</td>
				<td>${data[i].username}</td>
				<td>${data[i].boardCount}</td>
			</tr>
		`;
	}
	
	boardListTable.innerHTML = tableStr; //innerHTML은 덮어쓰기 때문에 while문 없어도됨 -> append를 할수도있기 때문에 씀*/
}

 for(let i=0; i<pageButton.length; i++){
	pageButton[i].onclick =() =>{
		nowPage = pageButton[i].textContent; //textContent-> div태그 사이 1,2,...
		load(nowPage);
	}	
}

function getBoardItems(){
	 const boardItems = document.querySelectorAll('.board-items');
	  console.log(boardItems);
}

 for(let i=0; i < boardItems.length; i++){
	boardItems[i].onclick =() => {
		alert(boardItems[i].querySelectorAll('td')[0].textContent);
	}
	
}

