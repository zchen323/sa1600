package sa1600.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import sa1600.security.JWT;

public class SecurityCheckFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("====== do filer");
		UserService userService = UserServiceFactory.getUserService();
		HttpServletRequest req = (HttpServletRequest)request;
		String thisUrl = req.getRequestURI();
		
		
		if(userService.isUserLoggedIn()){
			User user = userService.getCurrentUser();
			
			System.out.println("Logged in id: " + user.getUserId());
			System.out.println("Logged in domain: " + user.getAuthDomain());
			System.out.println("Logged in nickName: " + user.getNickname());
			System.out.println("Logged in email: " + user.getEmail());
			System.out.println("Logged in admin: " + userService.isUserAdmin());

			chain.doFilter(request, response);

		}else if(isAutenticateWithCustomerLogin(req)){
			chain.doFilter(request, response);
		}else{
			// redirect to login page
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/page1.html?errorMessage=pleaseCheckUsernameAndPassword");			
		}		
	}

	private boolean isAutenticateWithCustomerLogin(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String jwtToken = "";
		for(Cookie cookie : cookies){
			String cookieName = cookie.getName();
			if("jwt".equalsIgnoreCase(cookieName)){
				jwtToken = cookie.getValue();
				break;
			}
		}
		String userId = null;
		try{
			userId = JWT.validateJWT(jwtToken);
			User user = new User("user@email.com", ".com", userId);
			User loginUser = (User)request.getAttribute("LoginUser");
			if(loginUser != null){
				System.out.println("=== loginUser: " + loginUser.getUserId());
			}else{
				System.out.println("==== empty=====");
			}
			request.setAttribute("loginUser", user);
			
			System.out.println(">>>Logged in id: " + user.getUserId());
			System.out.println(">>>Logged in domain: " + user.getAuthDomain());
			System.out.println(">>>Logged in nickName: " + user.getNickname());
			System.out.println(">>>Logged in email: " + user.getEmail());
		}catch(Exception e){
			e.printStackTrace();
		}
		if(userId == null || userId.isEmpty())
			return false;
		return true;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
