package br.com.hfs.admin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.omnifaces.util.Beans;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmProfileService;

@FacesConverter(value = "admProfileConverter")
public class AdmProfileConverter implements Converter<AdmProfile> {

	private AdmProfileService admProfileService = Beans.getReference(AdmProfileService.class);

	@Override
	public AdmProfile getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			AdmProfile ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					String id = String.valueOf(((AdmProfile) o).getId());
					if (value.equals(id)) {
						ret = (AdmProfile) o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						String id = String.valueOf(((AdmProfile) o).getId());
						if (value.equals(id)) {
							ret = (AdmProfile) o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Long varId = Long.valueOf(value);
					ret = admProfileService.findById(varId).get();
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AdmProfile value) {
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
