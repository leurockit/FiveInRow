package com.tao.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.entity.Player;

/**
 * Servlet Filter implementation class GameFilter
 */
@WebFilter(filterName = "/GameFilter", urlPatterns = { "/*" })
public class GameFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public GameFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			res.setHeader("Cache-Control", "no-cache");
			String requestURI = req.getRequestURI();
			// only can access login page and login servlet for anonymous
			Player player = (Player) req.getSession().getAttribute("player");
			if (null == player) {
				if (!"/FiveInRow/login.jsp".equals(requestURI)
						&& !"/FiveInRow/Login".equals(requestURI)) {
					res.sendRedirect("login.jsp");
					return;
				}
			}
			// valid user.
			else {
				player.setAliveDate();
			}
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
