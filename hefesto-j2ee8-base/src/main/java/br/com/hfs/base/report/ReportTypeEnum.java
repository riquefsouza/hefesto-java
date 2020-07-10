package br.com.hfs.base.report;

public enum ReportTypeEnum {

	/** The pdf. */
	//Documentos
	PDF("application/pdf", "Portable Document Format (.pdf)"),
	
	/** The docx. */
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Word XML (.docx)"),
	
	/** The rtf. */
	RTF("application/rtf", "Rich Text Format (.rtf)"),
	
	/** The odt. */
	ODT("application/vnd.oasis.opendocument.text", "OpenDocument Text (.odt)"),

	//Planilhas
	/** The xlsx. */
	//XLS("application/vnd.ms-excel", "Microsoft Excel (.xls)"),
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Excel XML (.xlsx)"),
	
	/** The ods. */
	ODS("application/vnd.oasis.opendocument.spreadsheet", "OpenDocument Spreadsheet (.ods)"),

	/** The csv. */
	//Texto puro
	CSV("text/plain", "Comma Separated Values (.csv)"),
	
	/** The txt. */
	TXT("text/plain", "Text Only (.txt)"),	 	

	/** The pptx. */
	//Outros
	PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", "Microsoft Powerpoint XML (.pptx)"),	
	
	/** The html. */
	HTML("application/zip", "Hypertext Markup Language (.html)");
	
	private String typeContent;

	private String description;

	private ReportTypeEnum(String typeContent, String description) {
		this.typeContent = typeContent;
		this.description = description;
	}

	public String getTypeContent() {
		return typeContent;
	}

	public String getDescription() {
		return description;
	}

	
}
