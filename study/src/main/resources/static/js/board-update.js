

const inputItems = document.querySelectorAll('.input-items');
const textareaItem = document.querySelector('.textarea-item');
const submitBtn = document.querySelector('.submit-btn');

let path = window.location.pathname;

load();

 function load(){ 
	let boardCode = path.substring(path.lastIndexOf("/") + 1);//뒤에서부터 /찾음(n번째) ->/다음부터 ->자른다
	/*$.ajax({
		type: "get",
		url: `/api/board/${boardCode}`,
		dataType: "text",
		success: function(data){
			let boardObj = JSON.parse(data);
			getBoardDtl(boardObj.data);
		},
		error:function(){
			alert("비동기 처리 오류");
		}
	});*/
	
	let url = `/api/board/${boardCode}`;
	
	fetch(url)
	.then(response=>{
		console.log(response);
		if(response.ok){
			return response.json();
		}else{
			throw new Error("비동기 처리 오류");
		}
	})
	
	.then(result =>{
			getBoardDtl(result.data);
	})
	.catch(error=> {console.log(error)});
}
 
 function getBoardDtl(data){
	inputItems[0].value = data.title;
	inputItems[1].value = data.usercode;
	textareaItem.value = data.content;
}


submitBtn.onclick=()=>{
	submit();
}


function submit(){
	let boardCode = path.substring(path.lastIndexOf("/") + 1);
	let url = `/api/board/${boardCode}`;
	
	let option = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			title: inputItems[0].value,
			content: textareaItem.value
		})
	};
	
	fetch(url, option)
	.then(response => {
		console.log(response);
		if(response.ok){ 
			return response.json(); //data->
		}else{
			throw new Error("정상적인 데이터를 응답받지 못했습니다.");
		}
	})
	.then(data => {location.href ="/board-info/" + data.data;})
	.catch(error => console.log(error));
}
	









 