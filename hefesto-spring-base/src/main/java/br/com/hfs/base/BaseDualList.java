package br.com.hfs.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class BaseDualList.
 *
 * @param <T> the generic type
 */
public class BaseDualList<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The source. */
	private List<T> source = new ArrayList<T>();
	
	/** The target. */
	private List<T> target = new ArrayList<T>();
	
	/**
	 * Instantiates a new base dual list.
	 */
	public BaseDualList() {}
	
	/**
	 * Instantiates a new base dual list.
	 *
	 * @param source the source
	 * @param target the target
	 */
	public BaseDualList(List<T> source, List<T> target) {
		this.source = source;
		this.target = target;
	}
	
	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public List<T> getSource() {
		return source;
	}
	
	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(List<T> source) {
		this.source = source;
	}
	
	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public List<T> getTarget() {
		return target;
	}
	
	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(List<T> target) {
		this.target = target;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        if(getClass() != obj.getClass())
            return false;
        
        @SuppressWarnings("unchecked")
		final BaseDualList<T> other = (BaseDualList<T>) obj;
        
        if(this.source != other.source && (this.source == null || !this.source.equals(other.source)))
            return false;
        if(this.target != other.target && (this.target == null || !this.target.equals(other.target)))
            return false;
        
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.source != null ? this.source.hashCode() : 0);
        hash = 29 * hash + (this.target != null ? this.target.hashCode() : 0);
        return hash;
    }
}
