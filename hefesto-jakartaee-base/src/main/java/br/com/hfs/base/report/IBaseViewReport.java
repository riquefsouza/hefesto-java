package br.com.hfs.base.report;

import java.io.Serializable;

public interface IBaseViewReport extends Serializable {

	void export();

	String cancel();

}
