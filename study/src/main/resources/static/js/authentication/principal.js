/**
 * 세션정보 요청
 */
 
 
 async function getAuthenticationReq(){ 
	const url = "/api/v1/authentication"; 
	
	const response = await fetch(url); //동기처리가 됨(갔다가 와야지만 실행됨)
	if(response.ok){
		return response.json();  //promise로 리턴
	}else{
		throw new Error("Failed to get Authentication." + response);
	}
	
}