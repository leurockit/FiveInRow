package com.tao.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.entity.Hall;
import com.tao.entity.Player;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// no need login twice
//		Player player = (Player) request.getSession().getAttribute("player");
//		if (player != null && Hall.isPlayerInHall(player)) {
//			request.getRequestDispatcher("/hall.jsp")
//					.forward(request, response);
//			return;
//		}
		//clear application
		Hall.kickPlayers();
		// normal login
		// invalid name
		String playerName = request.getParameter("playerName");
		if (playerName == null || "".equals(playerName.trim())
				|| Hall.isPlayerInHall(playerName)) {
			request.setAttribute("errorMsg", "Name not valid!");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		// valid name
		Player player = new Player();
		player.setName(playerName);
		if (Hall.playerEnter(player) && Hall.playerSit(player)) {
			request.getSession().setAttribute("player", player);
			request.getRequestDispatcher("/hall.jsp")
					.forward(request, response);
			return;
		}
		request.setAttribute("errorMsg", "Game Hall is full.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

}
