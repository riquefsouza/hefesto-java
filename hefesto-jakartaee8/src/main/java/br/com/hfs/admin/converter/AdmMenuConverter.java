package br.com.hfs.admin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.omnifaces.util.Beans;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;

@FacesConverter(value = "admMenuConverter")
public class AdmMenuConverter implements Converter<AdmMenu> {

	private AdmMenuService admMenuService = Beans.getReference(AdmMenuService.class);

	@Override
	public AdmMenu getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			AdmMenu ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					String id = String.valueOf(((AdmMenu) o).getId());
					if (value.equals(id)) {
						ret = (AdmMenu) o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						String id = String.valueOf(((AdmMenu) o).getId());
						if (value.equals(id)) {
							ret = (AdmMenu) o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Long varId = Long.valueOf(value);
					ret = admMenuService.findById(varId).get();
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AdmMenu value) {
		try {
			if (value == null) {
				return "";
			} else {
				return String.valueOf(value.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
