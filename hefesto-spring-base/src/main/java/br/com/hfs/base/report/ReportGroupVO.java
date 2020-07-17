/**
 * <p><b>HFS Framework Spring</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2018
 */
package br.com.hfs.base.report;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReportGroupVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String group;
	
	private List<ReportTypeEnum> types;

	public ReportGroupVO() {
		super();
	}

	public ReportGroupVO(String group, List<ReportTypeEnum> types) {
		super();
		this.group = group;
		this.types = types;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<ReportTypeEnum> getTypes() {
		return types;
	}

	public void setTypes(List<ReportTypeEnum> types) {
		this.types = types;
	}

}
