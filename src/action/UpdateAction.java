package action;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.SearchVO;
import lombok.AllArgsConstructor;
import persistence.BoardDAO;
import upload.UploadUtil;

@AllArgsConstructor
public class UpdateAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//qna_board_modify.jsp 넘긴 값 가져오기
		BoardVO vo = new BoardVO();
		UploadUtil uploadUtil = new UploadUtil();
		HashMap<String, String> dataMap = uploadUtil.getItem(req);
		
		//파일첨부가 될 예정이기 때문에 WriteAction 참고해서  작성
		vo.setBno(Integer.parseInt(dataMap.get("bno")));
		vo.setTitle(dataMap.get("title"));
		vo.setContent(dataMap.get("content"));
		vo.setPassword(dataMap.get("password"));
		if(dataMap.containsKey("file")) {
		vo.setAttach(dataMap.get("file"));
		}
		BoardDAO dao = new BoardDAO();
		String password = dao.passwordCheck(vo.getBno());
		SearchVO search = null;
		String criteria="";
		String keyword ="";
		String page = dataMap.get("page");
		if(dataMap.containsKey("criteria")) {
			criteria = dataMap.get("criteria");
			keyword = URLEncoder.encode(dataMap.get("keyword"),"utf-8");
		}
		int result = 0;
		if(password.equals(vo.getPassword())) {
			result = dao.updateArticle(vo);
		}else {
			//비밀번호 틀림
			path = "qModify.do?bno="+vo.getBno()+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;
			return new ActionForward(path,true);
		}
		if(result>0) {
			path="qView.do?bno="+vo.getBno()+"&page="+page+"&criteria="+criteria+"&keyword="+keyword;
		}
		//수정이 완료되면 수정이 잘 되었는지 현재 게시물 보여주기
		return new ActionForward(path,true);
	}

}
