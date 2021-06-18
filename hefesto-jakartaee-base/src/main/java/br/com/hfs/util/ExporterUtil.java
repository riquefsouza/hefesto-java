package br.com.hfs.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

@Named
public final class ExporterUtil implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The excel opt. */
	private ExcelOptions excelOpt;
	
	/** The pdf opt. */
	private PDFOptions pdfOpt;

	/**
	 * Inicia o.
	 */
	@PostConstruct
	public void init() {
		excelOpt = new ExcelOptions();
		//excelOpt.setFacetBgColor("#F88017");
		excelOpt.setFacetFontSize("10");
		//excelOpt.setFacetFontColor("#0000ff");
		excelOpt.setFacetFontStyle("BOLD");
		//excelOpt.setCellFontColor("#00ff00");
		excelOpt.setCellFontSize("8");
		
		pdfOpt = new PDFOptions();
		//pdfOpt.setFacetBgColor("#F88017");
		pdfOpt.setFacetFontSize("10");
		//pdfOpt.setFacetFontColor("#0000ff");
		pdfOpt.setFacetFontStyle("BOLD");
		//pdfOpt.setCellFontColor("#00ff00");
		pdfOpt.setCellFontSize("8");
	}

	/**
	 * Gets the excel opt.
	 *
	 * @return the excel opt
	 */
	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}
	
	/**
	 * Gets the pdf opt.
	 *
	 * @return the pdf opt
	 */
	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}	

	/**
	 * Pre processa PDF.
	 *
	 * @param document
	 *            the document
	 * @param titulo
	 *            the titulo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BadElementException
	 *             the bad element exception
	 * @throws DocumentException
	 *             the document exception
	 */
	public void preProcessaPDF(Object document, String titulo)
			throws IOException, BadElementException, DocumentException {
		// cria o documento
		Document pdf = (Document) document;
		// seta a margin e p�gina, precisa estar antes da abertura do documento,
		// ou seja da linha: pdf.open()
		pdf.setMargins(1f, 1f, 1f, 1f);
		pdf.setPageSize(PageSize.A4.rotate());
		pdf.addTitle(titulo);
		pdf.open();
		// aqui pega o contexto para formar a url da imagem
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String logo = servletContext.getRealPath("") + File.separator + "resources/img" + File.separator + "logo.png";
		// cria a imagem e passando a url
		Image image = Image.getInstance(logo);
		// alinha ao centro
		image.setAlignment(Image.ALIGN_LEFT);
		// adciona a img ao pdf
		pdf.add(image);
		// adiciona um paragrafo ao pdf, alinha tamb�m ao centro
		Paragraph p = new Paragraph(titulo);
		p.setAlignment("center");
		p.setSpacingAfter(5f);
		pdf.add(p);		
	}

}
