package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.PageVO;
import domain.SearchVO;
import lombok.AllArgsConstructor;
import persistence.BoardDAO;

@AllArgsConstructor
public class SearchAction implements Action {

	private String path;

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 검색 폼에서 넘어오는 값 가져오기
		String criteria = req.getParameter("criteria");
		String keyword = req.getParameter("keyword");
		int page = 1;
		int amount = 10;
		
		if(req.getParameter("page")!=null) {
			page = Integer.parseInt(req.getParameter("page"));
		}

		// DB작업
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list = dao.getSearchList(page,amount,criteria, keyword);

		// 넘겨주기
		if (list.isEmpty()) {
			path="qList.do";
		} else {
			req.setAttribute("search",new SearchVO(criteria,keyword));
			req.setAttribute("list", list);
		
		}

		//검색조건에 맞는 게시물 수
		int totalRows = dao.getSearchRows(criteria, keyword);
		
		//총페이지수
		int totalPage = totalRows%10 ==0?totalRows/10:totalRows/10+1;
		
		//endpage
		int endPage = (int)(Math.ceil(page/10.0)*amount);
		//startpage
		int startPage = endPage -9; 
		
		//실제 마지막 페이지 구하기
		int realEnd = 0;
		if(endPage > totalPage) {
			realEnd = totalPage;
		}
		
		//Page정보 객체에 담고 페이지 이동
		PageVO pagevo = new PageVO();
		pagevo.setStartPage(startPage);
		pagevo.setEndPage(endPage);
		pagevo.setPage(page);
		pagevo.setTotalPage(totalPage);
		req.setAttribute("page", pagevo);
		
		return new ActionForward(path, false);
	}
}
