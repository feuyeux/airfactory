package creative.fire.jsfrf.jsf.pagination;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

/**
 * @author feuyeux@gmail.com
 * @version 1.0
 */
@ManagedBean(name = "displayResolution")
@ViewScoped
public class DisplayResolution implements Serializable {
	private static final long serialVersionUID = -4509764560269011152L;
	private int pageSize;
	private static transient Logger logger = Logger.getLogger(DisplayResolution.class);

	public DisplayResolution() {
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		logger.info("当前分页尺度：" + pageSize);
	}
}
