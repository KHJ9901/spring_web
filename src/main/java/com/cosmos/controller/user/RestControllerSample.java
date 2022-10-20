package com.cosmos.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosmos.dto.*;
import com.cosmos.service.*;

@RestController
@RequestMapping("/ex/")
public class RestControllerSample {

	private static final Logger log = LoggerFactory.getLogger("RestControllerSample.class");
	
	//Inject 도되고, Autowired 어노테이션도 되고
	@Inject
	BoardService bs;

	//produces : 해당 메소드가 생산하는 MIME 타입 => 문자열로 지정, MediaType 클래스로 지정  
	@GetMapping(value="getText", produces = "text/plain; charset=utf-8")
	public String getText() {
		
		log.info("MIME TYPE :" + MediaType.TEXT_PLAIN_VALUE);
		return "반갑습니다.";
	}
	
	//객체 반환 jackson-databind, Jackson Dataformat XML(객체는 json 아니면 xml 형태로 반환함)
	//gson : 자바객체를 json타입의 문자열로 변환  ==> 요즘 트렌드는 json을 사용. gson은 써야할게 많고 전송되는 데이터 양이 너무 많음
	
	@GetMapping(value="getBoard", produces = {MediaType.APPLICATION_JSON_VALUE, 
											  MediaType.APPLICATION_XML_VALUE})
	public Board getBoard() {  
		return bs.detailBoard("20");
		//프로젝트 실행하고 뒤에 ex/getBoard/json 치면 보드를 xml 파일 형태로 보여줌
		//요청방법: http://localhost:8080/ex/getBoard 찍어서 보드가 불러와지는지 확인.
		//요청방법: http://localhost:8080/ex/getBoard/json 이렇게 찍어서 json 형태로 불러와지는지 확인
	}
	
	//컬렉션 타입 객체 반환  => 리스트 타입을 던질때
	@GetMapping(value="getList")
	public List<Board> getList() {
		Criteria cri = new Criteria(1, 5);
		return bs.list(cri);
	}
	
	@GetMapping(value="getMap")
	public Map<String, Board> getMap() {
		Map<String, Board> map = new HashMap<>();
		map.put("Twenty", bs.detailBoard("20"));
		map.put("Twenty-first", bs.detailBoard("21"));
		return map;
	}
	
	//ResponseEntity 타입 ==> 요청 데이터가 정상인지 비정상인지 구분, 헤더에 상태코드값 전달 가능
	//메소드 매개변수 타입이 기본데이터타입(int, short, boolean 등)은 사용불가
	@GetMapping(value="check", params= {"page","record"})
	public ResponseEntity<List<Board>> check(Integer page, Integer record) {
		Criteria cri = new Criteria(page, record);
		
		List<Board> boardList = bs.list(new Criteria(page, record));
		
		ResponseEntity<List<Board>> result = null;
		
		if(page < 1 || record > 100) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(boardList);
		}	else {
			result = ResponseEntity.status(HttpStatus.OK).body(boardList);
		}
		return result;
	}
	//요청 방법 : http://localhost:8080/ex/check?page=1&record=5
	
	
	//파라미터가 url 경로에 포함된 경우 : @PathVariable
	//요청방법 : http://localhost:8080/board/43  => 보드의 seqno 43번을 가져와라
	//요청방법 : http://localhost:8080/board/43/page/1/record/5/title/좋은날
	@GetMapping("board/page/{pno}/record/{rno}/{key}/{value}") // => {sno} 시퀀스넘버, {pno} 페이지넘버, {rno} 레코드넘버
	public List<Board> getSearchBoard(
									 @PathVariable("pno") Integer currPage,
									 @PathVariable("rno") Integer rowPerPage,
									 @PathVariable("key") String searchItem,
									 @PathVariable("value") String searchValue
									 ) {
		
		Criteria cri = new Criteria(currPage, rowPerPage);
		cri.setSearchField(searchItem);
		cri.setSearchText(searchValue);
									 
		return bs.list(cri);
	}
	
	//@RequestBody : 요청 메세지를 Content-type 헤더에 지정된 값에 따라
	//	HttpMessageConvert를 사용해서 요청메세지를 반환함
	//	- ByteArrayMessageConverter : 바이트 배열로 변환
	//	- StringHttpMessageConverter : String 타입으로 변환
	//	- FromHttpMessageConverter : application/x-www-form-urlencoded
	//	     로 읽어서 MultiValueMap<String, String> 으로 변환
	//	- MultiValueMap<String, String> 을 application/x-www-form-urlencoded 또는 
	//	  multipart/form-data 메시지로 변환
	@PostMapping("member")
	public Member convert(@RequestBody Member member) {
		log.info("convert..member" + member);
		return member;
	}
	
	
	
}	
