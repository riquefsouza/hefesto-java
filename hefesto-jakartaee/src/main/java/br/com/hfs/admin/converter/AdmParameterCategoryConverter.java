package br.com.hfs.admin.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import org.omnifaces.util.Beans;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;

@FacesConverter(value = "admParameterCategoryConverter")
public class AdmParameterCategoryConverter implements Converter<AdmParameterCategory> {

	private AdmParameterCategoryService admParameterCategoryService = Beans.getReference(AdmParameterCategoryService.class);

	@Override
	public AdmParameterCategory getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			AdmParameterCategory ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					String id = String.valueOf(((AdmParameterCategory) o).getId());
					if (value.equals(id)) {
						ret = (AdmParameterCategory) o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						String id = String.valueOf(((AdmParameterCategory) o).getId());
						if (value.equals(id)) {
							ret = (AdmParameterCategory) o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Long varId = Long.valueOf(value);
					ret = admParameterCategoryService.findById(varId).get();
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AdmParameterCategory value) {
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
