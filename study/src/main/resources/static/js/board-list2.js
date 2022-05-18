
const boardListTable = document.querySelector('.board-list-table');
const boardListPage = document.querySelector('.board-list-page');


let nowPage = 1;

load(nowPage);

 function load(page){ 
	let url = "/board/list?page=" +page; //파라미터
	
	fetch(url)
	.then(response => { //responseentity
		if(response.ok){ //status -> ok
			return response.json(); // -> .then
		}else{
			throw new Error("비동기 처리 오류"); //->catch
		}
	})
	.then(result => { //cmrdto
			getBoardList(result.data); //return의 data(dto)
			createPageNumber(result.data[0].boardCountAll);
			getBoardItems();
	})
	.catch(error => {console.log(error)});
	
	
	/*$.ajax({
		type: "get",
		url: "/board/list",
		data: {
			"page":page
		},
		dataType: "text",
		success: function(data){
			let boardList = JSON.parse(data);
			getBoardList(boardList.data); //return의 data(dto)
			createPageNumber(boardList.data[0].boardCountAll);
			getBoardItems();
		},
		error:function(){
			alert("비동기 처리 오류");
		}
	});*/
}

 function createPageNumber(data){
	const boardListPage = document.querySelector('.board-list-page');
	const totalBoardCount = data;
	const totalPageCount = data % 5 == 0 ? data / 5 : (data / 5) +1;
	
	const startIndex = nowPage % 5 == 0 ? nowPage -4 : nowPage - (nowPage % 5) +1;
	const endIndex = startIndex + 4 <= totalPageCount ? startIndex + 4 : totalPageCount;
	
	let pageStr = ``;
	
	for(let i=startIndex; i <= endIndex; i++){
		pageStr += `<div>${i}</div>`;
	}
	
	pageStr += `<div>6</div>`;     
	
	boardListPage.innerHTML =pageStr;
	
	const pageButton = boardListPage.querySelectorAll('div');
	for(let i=0; i<pageButton.length; i++){
	pageButton[i].onclick =() =>{
		nowPage = pageButton[i].textContent;
		load(nowPage);
	}
}
}

 function getBoardList(data){
	/*while(boardListTable.hasChildNodes()){//자식노드가 있으며 반복
		boardListTable.removeChild(boardListTable.firstChild);
	}*/
	
	const tableBody =boardListTable.querySelector('tbody');
	let tableStr = ``;
	/*let tableStr= `
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
	</tr>
	`*/
	
	for(let i=0; i<data.length; i++){
		
		tableStr += `
			<tr class="board-items">
					<td>${data[i].boardCode}</td>
					<td>${data[i].title}</td>
					<td>${data[i].username}</td>
					<td>${data[i].boardCount}</td>
				</tr>
		`	;
	}
	
	tableBody.innerHTML = tableStr;
	/*boardListTable.innerHTML = tableStr; //innerHTML은 덮어쓰기 때문에 while문 없어도됨 -> append를 할수도있기 때문에 씀*/
	
	
	
}

 

//getboardlist가 호출되서 tr이 만들어지고 난후 
function getBoardItems(){
	 const boardItems = document.querySelectorAll('.board-items');
	 for(let i=0; i < boardItems.length; i++){
		boardItems[i].onclick =() => {
			location.href ="/board/dtl/" + boardItems[i].querySelectorAll('td')[0].textContent ;
		}
	}	
}




 