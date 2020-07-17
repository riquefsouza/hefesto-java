package br.com.hfs.util.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class PdfUtil.
 */
public final class PdfUtil implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = LogManager.getLogger(PdfUtil.class);

	public List<byte[]> separate(byte[] arquivoPDF) throws PdfException {
		List<byte[]> lista = new ArrayList<byte[]>();
		PDDocument doc = null;
		ByteArrayOutputStream bos;
		
		try {
			doc = PDDocument.load(arquivoPDF);
			
			Splitter splitter = new Splitter();
			List<PDDocument> paginas = splitter.split(doc);
					
			for (PDDocument item : paginas) {
				bos = new ByteArrayOutputStream();
	            item.save(bos);
	            lista.add(bos.toByteArray());
	            bos.close();
			}

			if( doc != null ) {
                doc.close();
            }    	
			
	    } catch (IOException e) {
	    	throw new PdfException(log, e.getMessage());        
	    }
		return lista;	
	}

	public byte[] join(List<byte[]> paginas) throws PdfException {
		byte[] arquivoSaida = null;
		ByteArrayInputStream bis;
		MemoryUsageSetting mus;
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
						
			PDFMergerUtility PDFmerger = new PDFMergerUtility(); 
			PDFmerger.setDestinationStream(bos);
			
			for (byte[] item : paginas) {
				bis = new ByteArrayInputStream(item);
				PDFmerger.addSource(bis);
				bis.close();
			}
			
			mus = MemoryUsageSetting.setupMainMemoryOnly();
			PDFmerger.mergeDocuments(mus);
			
            arquivoSaida = bos.toByteArray();
            
	    } catch (IOException e) {
	    	throw new PdfException(log, e.getMessage());        
	    }
		return arquivoSaida;
	}

	public byte[] joinAlternate(byte[] arquivoPDF1, byte[] arquivoPDF2) 
			throws PdfException {
		
		byte[] arquivoSaida = null;
		
		List<byte[]> lista1 = this.separate(arquivoPDF1);
		List<byte[]> lista2 = this.separate(arquivoPDF2);

		List<byte[]> listaJunta = new ArrayList<byte[]>();

		for (int i = 0; i < lista1.size(); i++) {
			listaJunta.add(lista1.get(i));
			listaJunta.add(lista2.get(i));
		}

		arquivoSaida = this.join(listaJunta);

		return arquivoSaida;
	}
	
}
