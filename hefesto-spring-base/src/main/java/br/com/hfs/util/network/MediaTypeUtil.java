package br.com.hfs.util.network;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

import br.com.hfs.base.report.ReportTypeEnum;

public final class MediaTypeUtil {

	public static List<MediaType> getByteArrayMediaTypes() {
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.IMAGE_GIF);
		list.add(MediaType.IMAGE_JPEG);
		list.add(MediaType.IMAGE_PNG);
		list.add(MediaType.APPLICATION_OCTET_STREAM);
		list.add(MediaType.APPLICATION_PDF);
		list.add(MediaType.APPLICATION_XML);
		list.add(MediaType.TEXT_HTML);
		list.add(MediaType.TEXT_PLAIN);
		list.add(ReportTypeEnum.DOCX.getMediaType());
		list.add(ReportTypeEnum.RTF.getMediaType());
		list.add(ReportTypeEnum.ODT.getMediaType());
		list.add(ReportTypeEnum.XLSX.getMediaType());
		list.add(ReportTypeEnum.ODS.getMediaType());
		list.add(ReportTypeEnum.PPTX.getMediaType());
		list.add(ReportTypeEnum.HTML.getMediaType());
		
		return list;
	}

	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		String mineType = servletContext.getMimeType(fileName);

		try {
			MediaType mediaType = MediaType.parseMediaType(mineType);
			return mediaType;
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
