/**************
 * 2022년 6월 16일 
 * 작성자 : 김희준
 * 내용 : 글작성 폼체크
 */
 
function formCheck() {
	var wt_title = document.forms["bdWriteForm"]["wt_title"].value;
		if(wt_title < 2) {
			alert("제목 길이는 최소 2글자 이상입니다");
			return false;
		} 
		
	var wt_open = document.forms["bdWriteForm"]["wt_open"].value; 
		if(wt_open == "") {
			document.getElementById("wt_sec").innerHTML = "'공개여부를 선택하세요'";
			return false;
		}
	}	
	var wt_content = document.forms["bdWriteForm"]["wt_content"].value;
		if(wt_content < 5) {
			alert("글 내용 길이는 최소 5글자 입니다");
			return false;
		}


