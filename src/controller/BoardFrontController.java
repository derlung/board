package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import action.BoardActionFactory;

@WebServlet("*.do")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardFrontController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //post 방식으로 보낼때 한글 깨짐 방지(get방식은 한글 안깨짐)
		
		String requestURI = request.getRequestURI(); //URI를 구분해서 적절한 action을 적용하기위해
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());
		
		BoardActionFactory baf = BoardActionFactory.getInstance();
		Action action = baf.action(cmd);
		
		ActionForward  af = null;
		try {
			af = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(af.isRedirect()) {

			response.sendRedirect(af.getPath());
			
		}else {

			RequestDispatcher rd = request.getRequestDispatcher(af.getPath());
			rd.forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
