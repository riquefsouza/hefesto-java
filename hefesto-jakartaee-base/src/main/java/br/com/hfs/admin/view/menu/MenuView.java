package br.com.hfs.admin.view.menu;

import java.util.List;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.base.BaseViewController;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MenuView extends BaseViewController {

	private MenuModel model;

    @PostConstruct
    public void init() {
    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

    	model = new DefaultMenuModel();

    	List<MenuVO> listMenu = getAuthenticatedUser().getListAdminMenus();
    	
    	listMenu.forEach(menu -> {
            DefaultSubMenu dSubmenu = DefaultSubMenu.builder()
                    .label(menu.getDescription())
                    .build();
    		
            menu.getSubMenus().forEach(submenu -> {
                DefaultMenuItem item = DefaultMenuItem.builder()
                        .value(submenu.getDescription())
                        .url(ec.getRequestContextPath() + "/private/" + submenu.getUrl())
                        .build();
                
                dSubmenu.getElements().add(item);            	
            });
        
            model.getElements().add(dSubmenu);
    	});
    	
        DefaultMenuItem menuExit = DefaultMenuItem.builder()
                .value("Exit")
                .icon("pi pi-fw pi-power-off")
                .command("#{hfsAuthenticator.logOut()}")
                .immediate(true)
                .build();
        
        model.getElements().add(menuExit);
    }
 
    public MenuModel getModel() {
        return model;
    }
    
}
