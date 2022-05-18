

const inputItems = document.querySelectorAll('.input-items');
const textareaItem = document.querySelector('.textarea-item');
const submitBtn = document.querySelector('.submit-btn');

/*let l =(a, b) =>{//function이 => / 매개변수 하나면 (괄호)필요없음 ,매개변수없으면 ()있어야함
	alert(a + b);
}; //test함수명을 l로 바꿈 / {중괄호}가 생략되면 return
l(10,20); -> 실행했을 때 return이 a+b
*/

/*
Promise : 비동기 처리를 위해 만들어진 객체
*/

function test(data){
	return new Promise((resolve, reject) => {
		if(data > 100){
			resolve(data); //resolve는 then이 있을 때 실행되고 then은 데이터를 전달		
		}else{
			throw reject(new Error("data가 100보다 작거나 같습니다.")); //reject => catch
		}							  
	})
}

//test호출
test(500) //호출하면 return
.then(testData => testData +100)
.then(testData2 => alert(testData2)) 
.catch(error => {console.log(error)});



submitBtn.onclick=()=>{
	submit();
}

/*function submit(){
	$.ajax({
		type: "post",
		url: "/board",
		contentType: "application/json",
		data: JSON.stringify({
			title: inputItems[0].value,
			content: textareaItem.value,
			usercode: inputItems[1].value
		}),
		dataType: "text",
		success: data => {
			let dataObj = JSON.parse(data);	
			alert(dataObj.msg);
			location.href = "/board/dtl/" + dataObj.data;
		},
		error: ()=>{
			alert("비동기 처리 오류");
		}
	});
}*/

function submit(){
	let url = "/board";
	
	let option = {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			title: inputItems[0].value,
			content: textareaItem.value,
			usercode: inputItems[1].value
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
	.then(data => {/*location.href ="/board/dtl/" + data.data;*/})
	.catch(error => console.log(error));
}
	









 