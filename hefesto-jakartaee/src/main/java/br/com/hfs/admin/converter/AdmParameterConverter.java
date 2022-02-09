package br.com.hfs.admin.converter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(value = "admParameterConverter")
public class AdmParameterConverter implements Converter<AdmParameter> {

	@Inject
	private AdmParameterService admParameterService;
	//private AdmParameterService admParameterService = Beans.getReference(AdmParameterService.class);

	@Override
	public AdmParameter getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			AdmParameter ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					String id = String.valueOf(((AdmParameter) o).getId());
					if (value.equals(id)) {
						ret = (AdmParameter) o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						String id = String.valueOf(((AdmParameter) o).getId());
						if (value.equals(id)) {
							ret = (AdmParameter) o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Long varId = Long.valueOf(value);
					ret = admParameterService.findById(varId).get();
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AdmParameter value) {
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
