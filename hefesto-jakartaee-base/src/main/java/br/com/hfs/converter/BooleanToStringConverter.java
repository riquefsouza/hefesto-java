package br.com.hfs.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// TODO: Auto-generated Javadoc
/**
 * The Class BooleanToStringConverter.
 */
@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

	/* (non-Javadoc)
	 * @see jakarta.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public String convertToDatabaseColumn(Boolean value) {
		return (value != null && value) ? "S" : "N";
	}

	/* (non-Javadoc)
	 * @see jakarta.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public Boolean convertToEntityAttribute(String value) {
		return "S".equals(value);
	}

}
