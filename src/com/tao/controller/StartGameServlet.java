package com.tao.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.entity.Hall;
import com.tao.entity.Player;
import com.tao.entity.Room;

/**
 * Servlet implementation class StartGameServlet
 */
@WebServlet("/StartGame")
public class StartGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartGameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Player player = (Player) request.getSession().getAttribute("player");
		//not valid player
//		if(player==null||!Hall.isPlayerInHall(player)){
//			request.setAttribute("errorMsg", "invalid player!");
//			request.getRequestDispatcher("/login.jsp").forward(request,
//					response);
//			return;
//		}
		Room room = Hall.findRoomByPlayer(player);
		//room must full
		if(room.isAvailable()){
			request.getRequestDispatcher("/hall.jsp").forward(request,
					response);
			return;
		}
		//player is ready
		player.setReady(true);
		for(int sec=0;sec<30;sec++){
			if(room.isAdversaryReady(player)){
				request.getSession().setAttribute("isBlack", room.isPlayerBlack(player));
				request.getSession().setAttribute("room", room);
				request.getRequestDispatcher("/room.jsp").forward(request,
						response);
				return;
			}
			try {
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("/hall.jsp").forward(request,
				response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
