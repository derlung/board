package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.SearchVO;
import lombok.AllArgsConstructor;
import persistence.BoardDAO;

@AllArgsConstructor
public class ReplyViewAction implements Action {
	
	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int bno = Integer.parseInt(req.getParameter("bno"));
		String page =req.getParameter("page");
		//검색 정보 가져오기
		String criteria = req.getParameter("criteria");
		String keyword = req.getParameter("keyword");
		
		//원본글 정보 가져오기
		BoardDAO dao = new BoardDAO();
		BoardVO vo = dao.getRow(bno);
		
		if(vo!=null) {
			req.setAttribute("page", page);
			req.setAttribute("vo", vo);
			req.setAttribute("search", new SearchVO(criteria,keyword));
		}else {
			
		}
		
		return new ActionForward(path,false);
	}

}
