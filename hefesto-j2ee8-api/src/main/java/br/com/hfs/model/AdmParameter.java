package br.com.hfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="ADM_PARAMETER")
@NamedQueries({
	@NamedQuery(name = "AdmParameter.getDescriptionById", query = "SELECT c.description FROM AdmParameter c WHERE c.id = ?1"),
	@NamedQuery(name = "AdmParameter.countNovo", query = "SELECT COUNT(c) FROM AdmParameter c WHERE LOWER(c.description) = ?1"),
	@NamedQuery(name = "AdmParameter.countAntigo", query = "SELECT COUNT(c) FROM AdmParameter c WHERE LOWER(c.description) <> ?1 AND LOWER(c.description) = ?2"),
	@NamedQuery(name = "AdmParameter.getValueByCode", query = "SELECT c.value FROM AdmParameter c WHERE c.code= ?1")
})
public class AdmParameter implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id	
	@GenericGenerator(name = "ADM_PARAMETER_ID_GENERATOR",
	strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
    	@Parameter(name = "sequence_name", value = "ADM_PARAMETER_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
	})		
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADM_PARAMETER_ID_GENERATOR")
	@Column(name="PAR_SEQ")
	private Long id;

	/** The code. */
	@NotBlank
	@Size(min=4, max=64)
	@Column(name="PAR_CODE", nullable = false, length = 64)
	private String code;

	/** The description. */
	@NotNull
	@NotBlank
	@NotEmpty	
	@Size(min=4, max=255)
	@Column(name="PAR_DESCRIPTION", unique = true, nullable = false, length = 255)
	private String description;

	/** The value. */
	@NotBlank
	@Size(min=4, max=4000)
	@Column(name="PAR_VALUE", nullable = false, length = 4000)
	private String value;
	
	/** The id adm parameter category. */
	@Column(name = "PAR_PMC_SEQ", nullable=false)
	private Long idAdmParameterCategory;

	/** The adm parameter category. */
	//bi-directional many-to-one association to AdmParameterCategory
	//@JsonSerialize(using = AdmParameterCategorySerializer.class)
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name="PAR_PMC_SEQ", nullable=false, insertable = false, updatable = false)
	private AdmParameterCategory admParameterCategory;

	/**
	 * Instantiates a new adm parameter.
	 */
	public AdmParameter() {
		limpar();
	}
	
	/**
	 * Limpar.
	 */
	public void limpar() {
		this.id = null;
		this.code = null;
		this.description = null;
		this.value = null;
		this.admParameterCategory = new AdmParameterCategory();
	}

	/**
	 * Pega o the id.
	 *
	 * @return o the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Atribui o the id.
	 *
	 * @param id
	 *            o novo the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Pega o the code.
	 *
	 * @return o the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Atribui o the code.
	 *
	 * @param code
	 *            o novo the code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Pega o the description.
	 *
	 * @return o the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Atribui o the description.
	 *
	 * @param description
	 *            o novo the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Pega o the value.
	 *
	 * @return o the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Atribui o the value.
	 *
	 * @param value
	 *            o novo the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Pega o the parameter category.
	 *
	 * @return o the parameter category
	 */
	public AdmParameterCategory getParameterCategory() {
		return this.admParameterCategory;
	}

	/**
	 * Atribui o the parameter category.
	 *
	 * @param parameterCategory
	 *            o novo the parameter category
	 */
	public void setParameterCategory(AdmParameterCategory parameterCategory) {
		this.admParameterCategory = parameterCategory;
	}

	/**
	 * Pega o the id parameter category.
	 *
	 * @return o the id parameter category
	 */
	public Long getIdParameterCategory() {
		return idAdmParameterCategory;
	}

	/**
	 * Atribui o the id parameter category.
	 *
	 * @param idParameterCategory
	 *            o novo the id parameter category
	 */
	public void setIdParameterCategory(Long idParameterCategory) {
		this.idAdmParameterCategory = idParameterCategory;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdmParameter other = (AdmParameter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}