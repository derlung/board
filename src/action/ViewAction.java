package action;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.SearchVO;
import lombok.AllArgsConstructor;
import persistence.BoardDAO;

@AllArgsConstructor
public class ViewAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//bno에 해당하는 값 가져오기
		int bno = Integer.parseInt(request.getParameter("bno"));
		String page = request.getParameter("page");
		//검색 정보 가져오기
		String criteria = request.getParameter("criteria");
		String keyword = request.getParameter("keyword");


		BoardDAO dao = new BoardDAO();
		//bno에 해당하는 내용 request 객체에 담기
		BoardVO vo = dao.getRow(bno);
		
		if(vo!=null) {	
			path+="?page="+page;
			request.setAttribute("search",new SearchVO(criteria,keyword));
			request.setAttribute("vo", vo);
		}
		
		
		return new ActionForward(path,false);
	}

}
