/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base.report;

import org.springframework.http.MediaType;

public enum ReportTypeEnum {
	
	/** The pdf. */
	PDF("Documentos", "application/pdf", "Portable Document Format (.pdf)", "pdf"),
	
	/** The docx. */
	DOCX("Documentos", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Word XML (.docx)", "docx"),
	
	/** The rtf. */
	RTF("Documentos", "application/rtf", "Rich Text Format (.rtf)", "rtf"),
	
	/** The odt. */
	ODT("Documentos", "application/vnd.oasis.opendocument.text", "OpenDocument Text (.odt)", "odt"),

	/** The xlsx. */
	//XLS("Planilhas", "application/vnd.ms-excel", "Microsoft Excel (.xls)"),
	XLSX("Planilhas", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Excel XML (.xlsx)", "xlsx"),
	
	/** The ods. */
	ODS("Planilhas", "application/vnd.oasis.opendocument.spreadsheet", "OpenDocument Spreadsheet (.ods)", "ods"),

	/** The csv. */
	CSV("Texto puro", "text/plain", "Valores Separados Por Vírgula (.csv)", "csv"),
	
	/** The txt. */
	TXT("Texto puro", "text/plain", "Somente Texto (.txt)", "txt"),

	/** The pptx. */
	PPTX("Outros", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "Microsoft Powerpoint XML (.pptx)", "pptx"),	
	
	/** The html. */
	//HTML("Outros", "text/html", "Linguagem de Marcação de Hipertexto (.html)");
	HTML("Outros", "application/zip", "Linguagem de Marcação de Hipertexto (.html)", "html");
	
	private String group;
	
	private String contentType;

	private String description;
	
	private String fileExtension;
	
	private MediaType mediaType;

	private ReportTypeEnum(String group, String contentType, String description, String fileExtension) {
		this.group = group;
		this.contentType = contentType;
		this.description = description;
		this.fileExtension = fileExtension;

		String[] part = this.contentType.split("/");
		
		String type = part[0];
		String subtype = part[1];
		
		this.mediaType = new MediaType(type, subtype);
	}
	
	public String getGroup() {
		return group;
	}

	public String getContentType() {
		return contentType;
	}

	public String getDescription() {
		return description;
	}

	public static String[] getGroups(){
		return new String[] { "Documentos","Planilhas","Texto puro","Outros" };
	}
	
	public String getFileExtension() {
		return fileExtension;
	}

	public MediaType getMediaType() {
		return mediaType;
	}
}
