package br.com.hfs.util;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

import br.com.hfs.security.HfsUserDetails;

public final class AuthenticationUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	//private static final Logger log = LoggerFactory.getLogger(AuthenticationUtil.class);

	public static Optional<HfsUserDetails> getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){ 
			Object principal = authentication.getPrincipal();
			
			if (principal instanceof HfsUserDetails) {
				HfsUserDetails userLogged = (HfsUserDetails) principal;
				return Optional.of(userLogged);
			}
		}
		return Optional.empty();
	}
	
	public static boolean isRedirectView(ModelAndView mv) {

		String viewName = mv.getViewName();
		if (viewName.startsWith("redirect:/")) {
			return true;
		}

		View view = mv.getView();
		return (view != null && view instanceof SmartView && ((SmartView) view).isRedirectView());
	}

	public static boolean isUserLogged() {
		try {
			return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
		} catch (Exception e) {
			return false;
		}
	}
	
}
