package nz.ac.massey.cs.webtech.s_16453897.server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class possiblemoves
 */
@WebServlet("/ttt/possiblemoves")
public class possiblemoves extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public possiblemoves() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
		    //if there is no session yet
			response.sendError(404);
		} else {
		//gets game
        tictactoeManager game = (tictactoeManager)session.getAttribute("game");
        //gets all free tiles 
        int[][] moves = game.availableMoves();
        //makes string to format 
        String cords="";
        for(int i =0;i<moves.length;i++) {
        	//line string for each line
        	String line="";
        	line+=moves[i][0]+1;
        	line+=",";
        	line+=moves[i][1]+1;
        	line+="\n";
        	//add to the return string
        	cords+=line;
        }
        //sets content type to match the sent value
        response.setContentType("text/plain");
        //sends all cords
        response.getWriter().print(cords);
        
        
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
