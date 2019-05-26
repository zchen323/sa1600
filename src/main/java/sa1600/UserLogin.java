package sa1600;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import sa1600.security.JWT;

@WebServlet(
	    name = "UserAPI",
	    description = "UserAPI: Login / Logout with UserService",
	    urlPatterns = "/j_security_check"
	)
public class UserLogin extends javax.servlet.http.HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UserService userService = UserServiceFactory.getUserService();
	
		
		String thisUrl = request.getRequestURI();
		response.setContentType("text/html");
		if(request.getUserPrincipal() != null){
			response.getWriter()
				.println(
					"<p>Hello, " + request.getUserPrincipal().getName()
					+ "! You can <a href=\""
					+ userService.createLogoutURL(thisUrl)
					+ "\">sign out</a>. </p>");
					
		}else{
			response.getWriter()
				.println("<p>Please <a href=\""
				+ userService.createLoginURL(thisUrl) + "\">sign in");
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userId = request.getParameter("uname");
		String password = request.getParameter("pwd");

		String token = JWT.createJWT(userId, 1000*60*60);    // ttl one hour 
		Cookie cookie = new Cookie("jwt", token);
		response.addCookie(cookie);
		response.sendRedirect("/test/hello");
		response.flushBuffer();
		
	}

}
