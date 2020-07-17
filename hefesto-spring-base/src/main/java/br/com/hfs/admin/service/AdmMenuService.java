package br.com.hfs.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.repository.AdmMenuRepository;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.base.BaseService;

@Service
public class AdmMenuService extends BaseService<AdmMenu, Long, AdmMenuRepository> {

	private static final long serialVersionUID = 1L;

	private static List<AdmMenu> cacheMenuEstaticos = new ArrayList<AdmMenu>();

	public AdmMenuService() {
		super(AdmMenu.class);
	}
	
	public Page<AdmMenu> findByDescriptionLike(String description, Pageable pagination){
		return repository.findByDescriptionLike(description, pagination);
	}
	
	public List<AdmMenu> findByDescriptionLike(String description){
		return repository.findByDescriptionLike(description);
	}
	
	public List<AdmMenu> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_MENU", "MNU_SEQ", pageNumber, pageSize);
	}

	public List<AdmMenu> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_MENU", "MNU_SEQ", startInterval, endInterval);
	}
	
	public List<AdmMenu> findChildrenMenus(AdmMenu menu){
		return repository.findChildrenMenus(menu);
	}

	@Transactional
	private AdmMenu updateSubmenu(AdmMenu menuPai) {
		if ((menuPai.getAdmSubMenus() != null) && (!menuPai.getAdmSubMenus().isEmpty())) {
			for (AdmMenu menu : menuPai.getAdmSubMenus()) {
				update(menu);
			}
		}
		return menuPai;
	}
	
	@Transactional
	public AdmMenu saveOrUpdateDragReordering(AdmMenu menuPaiAntigo, AdmMenu menuPaiNovo, AdmMenu menuMover) {
		cacheMenuEstaticos.clear();
		if ((menuPaiAntigo != null) && (!menuPaiAntigo.equals(menuPaiNovo))) {
			menuPaiAntigo = updateSubmenu(menuPaiAntigo);
		}
		if (menuPaiNovo != null) {
			menuPaiNovo = updateSubmenu(menuPaiNovo);
		}
		menuMover = findById(menuMover.getId().longValue()).get();
		menuMover.setAdmMenuParent(menuPaiNovo);
		return update(menuMover);
	}
	
	@Transactional
	public AdmMenu saveOrUpdate(AdmMenu novoItemMenu) {
		cacheMenuEstaticos.clear();
		if ((novoItemMenu.getAdmMenuParent() != null) && (novoItemMenu.getAdmMenuParent().getId() != null)) {
			novoItemMenu.setAdmMenuParent((AdmMenu) findById(novoItemMenu.getAdmMenuParent().getId().longValue()).get());
		}
		return (AdmMenu) update(novoItemMenu);
	}
		
	@Transactional
	public void delete(AdmMenu menu) {
		cacheMenuEstaticos.clear();
		List<AdmMenu> listaMenuFilhos = repository.findChildrenMenus(menu);
		if ((listaMenuFilhos != null) && (!listaMenuFilhos.isEmpty())) {
			for (AdmMenu menuFilho : listaMenuFilhos) {
				delete(menuFilho);
			}
		}
		menu = (AdmMenu) findById(menu.getId().longValue()).get();
		if (menu != null) {
			delete(menu);
		}
	}

	public List<MenuVO> toListMenuVO(List<AdmMenu> listaMenu){
		List<MenuVO> lista = new ArrayList<MenuVO>();
		for (AdmMenu menu : listaMenu) {
			lista.add(menu.toMenuVO());
		}		
		return lista;
	}

}
