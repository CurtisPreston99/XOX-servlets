package nz.ac.massey.cs.webtech.s_16453897.server;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class move
 */
@WebServlet("/ttt/move")
public class move extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public move() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//gets session if there is one 
		HttpSession session = request.getSession(false);
		if (session == null) {
		    //if there is no session yet
			response.sendError(404);
		} else {
		//gets game object
		tictactoeManager game = (tictactoeManager)session.getAttribute("game");
		PrintWriter out = response.getWriter();
		String info=request.getPathInfo();
			out.println(info);
		//matching to regex of what the url should have 
		if(info.matches("/x[1-3]y[1-3]")) {
			//gets x and y
			Integer x=Integer.valueOf(info.charAt(2))-49;

			Integer y=Integer.valueOf(info.charAt(4))-49;
			//moves player
			if(game.PlayerMove(x,y)) {
				
			}else {
//				if the player makes an invalid move
				response.sendError(400);
			}
		}else {
//			if the player sends a malformed url 
			response.sendError(400);
		}
		//computer has a turn if no one has won the game yet
		if(game.gameoverCheck().equals("none")) {
			game.AI();
		}
	}
	}

}
