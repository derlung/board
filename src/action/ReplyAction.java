package action;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import lombok.AllArgsConstructor;
import persistence.BoardDAO;
import upload.UploadUtil;

@AllArgsConstructor
public class ReplyAction implements Action {
	private String path;

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		BoardDAO dao = new BoardDAO();
		//qna_board_reply 값 가져오기
		UploadUtil upload = new UploadUtil();
		HashMap<String,String> mapData = upload.getItem(req);
		
		BoardVO vo = new BoardVO();
		
		vo.setName(mapData.get("name"));
		vo.setTitle(mapData.get("title"));
		vo.setBno(Integer.parseInt(mapData.get("bno")));
		vo.setContent(mapData.get("content"));
		vo.setPassword(mapData.get("password"));
		if(mapData.containsKey("file"))
			vo.setAttach(mapData.get("file"));
		
		String page =mapData.get("page");
		//검색 정보 가져오기
		String criteria = null;
		String keyword = null;
		if(mapData.containsKey("criteria")) {
			criteria = mapData.get("criteria");
			keyword = URLEncoder.encode(mapData.get("keyword"),"utf-8");
		}
		
		//원본글의 hidden 태그 값
		vo.setRe_lev(Integer.parseInt(mapData.get("re_lev")));
		vo.setRe_ref(Integer.parseInt(mapData.get("re_ref")));
		vo.setRe_seq(Integer.parseInt(mapData.get("re_seq")));
		
		int result = dao.replayArticle(vo);
		if(result==0) {
			path="";
		}else {
			if(!criteria.isEmpty()) {
				path="qSearch.do?page="+page+"&criteria="+criteria+"&keyword="+keyword;
			}else {
				path="qSearch.do?page="+page;
			}
		}
		
		return new ActionForward(path,true);
	}

}
