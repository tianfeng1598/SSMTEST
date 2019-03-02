package tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import entity.User;

/**
 * 全局拦截器,用于验证用户是否登录
 * 
 * @author Tian
 *
 */
public class SysIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("user");
		if (userLogin == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return false;
		} else {
			return true;
		}
	}
}
