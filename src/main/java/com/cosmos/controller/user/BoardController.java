package com.cosmos.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cosmos.common.LoginImpl;
import com.cosmos.dto.Board;
import com.cosmos.dto.Criteria;
import com.cosmos.dto.Page;
import com.cosmos.service.BoardService;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//@GetMapping("list")
	//@PostMapping("list")
	@RequestMapping(value="list", method= {RequestMethod.POST, RequestMethod.GET})
	public String list(Criteria cri, Model model) {
		//게시판 리스트			
		/*String searchField = cri.getSearchField();
		String searchText = cri.getSearchText();
		int currentPage = cri.getCurrentPage();
		int rowPerPage = cri.getRowPerPage();
		Criteria cri2 = new Criteria(currentPage, rowPerPage);
		cri2.setSearchField(searchField);
		cri2.setSearchText(searchText);*/
		
		if(cri.getCurrentPage() == 0) cri.setCurrentPage(1);
		if(cri.getRowPerPage() == 0) cri.setRowPerPage(10);
		
		List<Board> board = boardService.list(cri);
		
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri));			
		model.addAttribute("board", board);
		return "/board/boardPage";
	}
	
	@GetMapping("regForm")
	public void regForm() {
		//return "/board/detail.jsp";
	}
	
	@PostMapping("register")
	public String register(Board board, 
						   MultipartFile filename,
						   HttpSession sess,
						   RedirectAttributes rttr) { //리다이렉트 할때 필요함
		
		board.setId(((LoginImpl)sess.getAttribute("loginUser")).getId());
		
		rttr.addFlashAttribute("seqno", boardService.insertBoard(board, filename)); 
			// addFlash는 객체를 담음
		
		return "redirect:/board/detail";
	}
	
	@GetMapping("detail") 
	public String detail(@ModelAttribute("seqno") String seqno, Model model) {
		
		model.addAttribute("board", boardService.detailBoard(seqno));
		   // addAttribute 는 스트링을 담음
		
		return "/board/detail";
	}
	
}
	
/*
	if(cmd.equals("boardList.bo")) {
		
	} else if(cmd.equals("boardDetail.bo")) {
		//게시판 세부내용 출력			
		String seqno = req.getParameter("seqno");
		if(seqno == null) {
			seqno = (String)req.getAttribute("seqno");
		}			
		req.setAttribute("board", boardService.searchBoard(seqno));
		
		String page = req.getParameter("page");
		
		if(page != null) {
			goView(req, resp, "/board/modify.jsp");
		} else {
			goView(req, resp, "/board/detail.jsp");				
		}
	} else if(cmd.equals("boardRegForm.bo")) {
		goView(req, resp, "/board/boardReg.jsp");
	} else if(cmd.equals("boardReg.bo")) {			
		req.setAttribute("seqno", boardService.insertBoard(req, resp));
		goView(req, resp, "boardDetail.bo");
	} else if(cmd.equals("modify.bo")) {
		req.setAttribute("seqno", boardService.update(req, resp));
		goView(req, resp, "boardDetail.bo");
	} else if(cmd.equals("boardDelete.bo")){
	    boardService.delete(req.getParameter("seqno"));
	    goView(req, resp, "boardList.bo");
	}
	
}
	
	void goView(HttpServletRequest req, HttpServletResponse resp, String viewPage) throws ServletException, IOException {		
		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
		rd.forward(req, resp);
	}

}
*/