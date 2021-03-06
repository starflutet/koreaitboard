package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.model.UserVO;

public class Utils {
	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO)hs.getAttribute("loginUser");
	}
	public static void logout(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		hs.invalidate();
	}
}
