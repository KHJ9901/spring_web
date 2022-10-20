/**************
 * 2022년 6월 2일 
 * 작성자 : 김희준
 * 내용 : 회원가입 폼체크
 */

function idCheck() {
	//	id 값이 없는 경우 '아이디를 입력하세요' 경고창 뜨고, 
	// 커서를 아이디 입력란에 포커스되게끔
	var id = document.forms["memRegForm"]["id"].value;
	
	var x = new XMLHttpRequest();
	
	//전송방식, 요청문서, 비동기식 요청
	x.open("GET", "/member/idDoubleCheck?id=" + id , true); // 객체지향(밑의x들과 동시에 작동)
	x.send();
	x.onreadystatechange = function() {

		var msg = document.getElementById("idCheckMsg");

		// 4: request finished and response is ready
		if(x.readyState === 4 && x.status === 200) {	// === 은 타입까지 일치하는것으로 인식
				console.log("ok");
				var rsp = x.responseText.trim();
				document.getElementById("isIdCheck").value = rsp ; // 문자 앞뒤 공백 삭제
				
				if(rsp == 0) {
					msg.innerText = "사용 가능한 아이디입니다."
				} else {
					msg.innerText = "중복된 아이디 입니다."
				}
		} else {
				//오류발생 : 403, 404,
				console.log("서버에러(403,404)");
		}
	};
}

function formCheck() {
	// 비밀번호 체크 : document.forms["폼이름"]["비번이름"]
	var	pw = document.forms["memRegForm"]["pw"].value;
		// alert(pw.length); // (괄호안의 문자가 뜸) (문자열은"", 한글자는'')
		if(pw.length < 6) {
			alert("비밀번호 문자길이를 확인하세요");
			return false;
		}

	var	repw = document.forms["memRegForm"]["repw"].value;
		if(pw != repw) {
			alert("비번 재입력 오류");
			return false;
		}

		//이름체크
		if(document.forms["memRegForm"]["name"].value.length < 1){
			// alert("이름을 입력하세요") --> 경고창 띄우는거 
			document.getElementById("msg").innerHTML = "이름을 입력하세요"; // -> 바로 밑에 글 띄우기
			return false;
	}
	
	//성별체크
	var gender = document.forms["memRegForm"]["gender"].value; 
		if(gender == "") {
			document.getElementById("gen").innerHTML = "'성별을 선택하세요'";
			return false;
		}

	//취미체크 
	var hobby_length = document.forms["memRegForm"]["hobby"].length; 
		//alert(hobby_length)
		//for(초기값; 조건식; 증가 or 감소){
		//	실행문;
		for(var i=0; i < hobby_length ; i++){
			if(document.forms["memRegForm"]["hobby"][i].checked){ //[폼이름찾기][어떤객체(이름)][개수]
				// alert(i + "번째 취미가 선택되었음");
				console.log(i + "번째 취미가 선택됨")
				break;
			}
		}
		
		if(i == hobby_length) {
			return false;	
		}
}

function inputDomain() {
	console.log("도메인선택항");
	var sel = document.forms["memRegForm"]["selDomain"].value;
	console.log("선택 옵션값 : " + sel)
	document.forms["memRegForm"]["domain"].value = sel;
	
	if(sel != ""){
		document.forms["memRegForm"]["domain"].readOnly = true;
		document.forms["memRegForm"]["domain"].style.backgroundColor ='lightyellow';
	} else {
		document.forms["memRegForm"]["domain"].readOnly = false;
		document.forms["memRegForm"]["domain"].focus();		// 직접입력 선택시 텍스트 창 자동 선택
		document.forms["memRegForm"]["domain"].style.backgroundColor ='lightblue';
		}
}