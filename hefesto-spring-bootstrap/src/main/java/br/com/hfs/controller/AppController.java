package br.com.hfs.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.unbescape.html.HtmlEscape;

import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.security.HfsUserDetails;

@Controller
@RequestMapping("/")
@SessionAttributes("listMenu")
public class AppController {
	
	private List<MenuVO> listMenu;

	@RequestMapping("/")
    public String root(Locale locale) {
        return "redirect:/home";
    }

	@ModelAttribute("listMenu")
	public List<MenuVO> listMenu() {
		return listMenu;
	}
	 
    /** Home page. */
    @RequestMapping("/home")
    public ModelAndView index() {
    	ModelAndView mv = new ModelAndView("index.html");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		HfsUserDetails userDetails = (HfsUserDetails)authentication.getPrincipal();

		listMenu = userDetails.getAuthenticatedUser().getListAdminMenus();
		mv.addObject("listMenu", listMenu);

        return mv;
    }
    
    /** Login form. */
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
        
    /** Error page. */
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error";
    }

    /** Error page. */
    @RequestMapping("/403.html")
    public String forbidden() {
        return "403";
    }
    
    @RequestMapping("/sessionExpired.html")
    public String sessionExpired() {
        return "sessionExpired";
    }
	
}
