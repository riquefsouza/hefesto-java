package br.com.hfs;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.inject.Named;

@Named
public class ApplicationConfig implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private boolean enableProfileControl = false;

	private boolean showAdministrativeMenu = false;

	private String dtLines;

	private String dtLinesPerPageTemplate;

	private String dtPagerPosition;

	private String dtPagerModel;

	private String dtPagerReportModel;
	
	private ResourceBundle bundle;

	public ApplicationConfig() {
		super();

		// FacesContext context = FacesContext.getCurrentInstance();
		// Locale locale = context.getViewRoot().getLocale();

		Locale locale = new Locale("pt", "BR");
		bundle = ResourceBundle.getBundle("application", locale);

		load();
	}
	
	public ApplicationConfig(ResourceBundle bundle) {
		this.bundle = bundle;
		
		load();
	}
	
	private void load() {
		if (bundle != null) {
			this.enableProfileControl = Boolean.parseBoolean(bundle.getString("enableProfileControl"));
			this.showAdministrativeMenu = Boolean.parseBoolean(bundle.getString("showAdministrativeMenu"));
			this.dtLines = bundle.getString("dtLines");
			this.dtLinesPerPageTemplate = bundle.getString("dtLinesPerPageTemplate");
			this.dtPagerPosition = bundle.getString("dtPagerPosition");
			this.dtPagerModel = bundle.getString("dtPagerModel");
			this.dtPagerReportModel = bundle.getString("dtPagerReportModel");
		} else {
			enableProfileControl = true;
			showAdministrativeMenu = false;
			dtLines = "20";
			dtLinesPerPageTemplate = "10,20,50,80,100";
			dtPagerPosition = "top";
			dtPagerModel = "{Exporters} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
			dtPagerReportModel = "({startRecord} to {endRecord} total {totalRecords} - Page: {currentPage}/{totalPages})";
		}
	}
	
	public boolean isEnableProfileControl() {
		return enableProfileControl;
	}

	public void setEnableProfileControl(boolean enableProfileControl) {
		this.enableProfileControl = enableProfileControl;
	}

	public boolean isShowAdministrativeMenu() {
		return showAdministrativeMenu;
	}

	public void setShowAdministrativeMenu(boolean showAdministrativeMenu) {
		this.showAdministrativeMenu = showAdministrativeMenu;
	}

	public String getDtLines() {
		return dtLines;
	}

	public void setDtLines(String dtLines) {
		this.dtLines = dtLines;
	}

	public String getDtLinesPerPageTemplate() {
		return dtLinesPerPageTemplate;
	}

	public void setDtLinesPerPageTemplate(String dtLinesPerPageTemplate) {
		this.dtLinesPerPageTemplate = dtLinesPerPageTemplate;
	}

	public String getDtPagerPosition() {
		return dtPagerPosition;
	}

	public void setDtPagerPosition(String dtPagerPosition) {
		this.dtPagerPosition = dtPagerPosition;
	}

	public String getDtPagerModel() {
		return dtPagerModel;
	}

	public void setDtPagerModel(String dtPagerModel) {
		this.dtPagerModel = dtPagerModel;
	}

	public String getDtPagerReportModel() {
		return dtPagerReportModel;
	}

	public void setDtPagerReportModel(String dtPagerReportModel) {
		this.dtPagerReportModel = dtPagerReportModel;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public String toString() {
		return "ApplicationConfig [enableProfileControl=" + enableProfileControl + ", showAdministrativeMenu="
				+ showAdministrativeMenu + ", dtLines=" + dtLines + ", dtLinesPerPageTemplate=" + dtLinesPerPageTemplate
				+ ", dtPagerPosition=" + dtPagerPosition + ", dtPagerModel=" + dtPagerModel + ", dtPagerReportModel="
				+ dtPagerReportModel + ", bundle=" + bundle + "]";
	}

}
