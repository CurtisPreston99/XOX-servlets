package nz.ac.massey.cs.webtech.s_16453897.server;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class state
 */
@WebServlet("/ttt/state")
public class state extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public state() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null) {
		    //if there is no session yet
			response.sendError(404);
		} else {
	        tictactoeManager game = (tictactoeManager)session.getAttribute("game");	
	        //if the url wants a png
			if(request.getParameter("format").equals("png")) {
				//get output stream
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("image/png");
				//get image and send it as png
		        javax.imageio.ImageIO.write(game.toBufferedImage(), "png", out);
		        //set content type to match content
		        response.setContentType("image/png");

			}else {
				//if game is text based
		        response.setContentType("text/plain");
		        PrintWriter out = response.getWriter();
		        out.print(game.toString());
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
