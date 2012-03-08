package com.tao.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tao.entity.Player;
import com.tao.entity.Room;

/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/Game")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// impl put chess piece
		Room room = (Room) request.getSession().getAttribute("room");
		if (room.isVictory()) {
			System.out.println("victory!");
			return;
		}
		Player player = (Player) request.getSession().getAttribute("player");
		Integer x = Integer.valueOf(request.getParameter("x"));
		Integer y = Integer.valueOf(request.getParameter("y"));
		String result = room.put(x, y, player);
		if (result == null) {
			return;
		}
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();

		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Room room = (Room) request.getSession().getAttribute("room");
		Player player = (Player) request.getSession().getAttribute("player");
		String restart = request.getParameter("restart");
		// restart game
		if ("0".equals(restart)) {
			System.out.println("game restart..............!");
			room.restart(player);
			for (int sec = 0; sec < 30; sec++) {
				if (room.isAdversaryReady(player)) {
					return;
				}
				try {
					Thread.currentThread();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		// get chess piece
		String result = room.get(player);
		while (result == null) {
			result = room.get(player);
		}
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
		return;
	}

}
