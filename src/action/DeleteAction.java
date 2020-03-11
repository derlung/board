package action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import persistence.BoardDAO;

@AllArgsConstructor
public class DeleteAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setCharacterEncoding("utf-8");
		int page = Integer.parseInt(req.getParameter("page"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		String password = req.getParameter("password");
		
		//검색 정보 가져오기
		String criteria = req.getParameter("criteria");
		String keyword = req.getParameter("keyword");
		
		BoardDAO dao = new BoardDAO();
		String password_check = dao.passwordCheck(bno);
		if(!password.equals(password_check)) {
			//비밀번호가 틀렸을때
			return new ActionForward("view/qna_board_pwdCheck.jsp?bno="+bno+"&page="+page+"&criteria="+criteria+"&keyword="+keyword,true);
		}
		int result = dao.deleteArticle(bno);
		if(result>0) {
			//해당하는게 있을때
			if(criteria.isEmpty()) {
				path+="?bno="+bno+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;	
			}else {
				path ="qSearch.do"+"?bno="+bno+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;
			}
			
		}else {
			//해당하는게 없을때
			path="view/qna_board_pwdCheck.jsp?bno="+bno+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;
		}
		return new ActionForward(path,true);
	}

}
