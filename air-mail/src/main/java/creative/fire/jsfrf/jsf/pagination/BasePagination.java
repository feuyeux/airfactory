package creative.fire.jsfrf.jsf.pagination;

import java.util.ArrayList;

import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

/**
 * @author feuyeux@gmail.com
 * @version 1.0
 */
public abstract class BasePagination implements IPagination {
	transient static Logger log = Logger.getLogger(BasePagination.class);
	protected int pageIndex = 1;// 当前页号
	protected int pageSize;// 每页显示多少项
	//protected int pageCount;// 共有多少页
	protected int totalSize;// 共有多少项

	private final int left = 4;
	private final int right = 5;
	private final int pagePageSize = 10;

	protected boolean disabledFirst;
	protected boolean disabledLast;

	protected ArrayList<Integer> pageNumbers = new ArrayList<Integer>();
	protected ArrayList<SelectItem> pageNumberItems;

	@ManagedProperty(value = "#{displayResolution}")
	protected DisplayResolution displayResolution;

	public BasePagination() {
		BasePagination.log.debug("构造");
	}

	protected void filpBar() {
		flipBar1();
		flipBar2();
	}

	protected void flipBar1() {
		if (pageNumberItems == null) {
			pageNumberItems = new ArrayList<SelectItem>();
		} else {
			pageNumberItems.clear();
		}

		if (getTotalSize() == 0) {
			pageNumberItems.add(new SelectItem(1 + "", 1 + ""));
		} else {
			for (int i = 1; i <= getPageCount(); i++) {
				String pageNumber = "" + i;
				pageNumberItems.add(new SelectItem(pageNumber, pageNumber));
			}
		}
	}

	protected void flipBar2() {
		if (totalSize == 0) {
			pageIndex = 1;
			totalSize = 1;
			pageNumbers.clear();
			return;
		}

		int mod = pageIndex % pagePageSize;
		int pagePageIndex;
		if (mod > 0) {
			pagePageIndex = pageIndex / pagePageSize + 1;
		} else {
			pagePageIndex = pageIndex / pagePageSize;
		}

		int pageStart = pageIndex - left;
		int pageEnd = pageIndex + right;
		int pageCount = getPageCount();

		if (pageStart < 1) {
			pageStart = 1;
		} else if (pageStart > 1 && pageCount <= pagePageSize)
			pageStart = 1;
		if (pageEnd > pageCount) {
			pageEnd = pageCount;
		} else if (pageEnd < pagePageSize && pageCount >= pagePageSize) {
			pageEnd = pagePageSize;
		} else if (pageEnd < pageCount && pageEnd < pagePageSize) {
			pageEnd = pageCount;
		}

		if (pageEnd > totalSize) {
			int revisedPageIndex = (pagePageIndex - 1) * pagePageSize + 1;
			pageStart = revisedPageIndex;
			pageEnd = totalSize;
		}

		pageNumbers.clear();
		if (pageEnd > 1) {
			for (int i = pageStart; i <= pageEnd; i++) {
				pageNumbers.add(i);
			}
		}
	}

	public DisplayResolution getDisplayResolution() {
		return displayResolution;
	}

	@Override
	public int getPageCount() {
		int mod = getTotalSize() % pageSize;
		int pageCount = getTotalSize() / pageSize;
		if (mod > 0) {
			pageCount++;
		}

		if (pageCount < 1) {
			pageCount = 1;
		}
		return pageCount;
	}

	@Override
	public int getPageIndex() {
		return pageIndex;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getTotalSize() {
		return totalSize;
	}

	public void init() {
		if (displayResolution == null) {
			pageSize = 20;
		} else {
			pageSize = displayResolution.getPageSize();
			if (pageSize == 0) {
				pageSize = 20;
			}
		}
	}

	@Override
	public boolean isDisabledFirst() {
		disabledFirst = getPageIndex() == 1;
		return disabledFirst;
	}

	@Override
	public boolean isDisabledLast() {
		disabledLast = getPageIndex() == getPageCount();
		return disabledLast;
	}

	public ArrayList<Integer> getPageNumbers() {
		return pageNumbers;
	}

	public void setPageNumbers(ArrayList<Integer> pageNumbers) {
		this.pageNumbers = pageNumbers;
	}

	public ArrayList<SelectItem> getPageNumberItems() {
		return pageNumberItems;
	}

	public void setPageNumberItems(ArrayList<SelectItem> pageNumberItems) {
		this.pageNumberItems = pageNumberItems;
	}

	public void setDisplayResolution(DisplayResolution displayResolution) {
		this.displayResolution = displayResolution;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public void first() {
		setPageIndex(1);
		flipover();
	}

	@Override
	public void last() {
		setPageIndex(getPageCount());
		flipover();
	}

	@Override
	public void next() {
		pageIndex++;
		if (pageIndex > totalSize) {
			pageIndex = totalSize;
			return;
		}
		flipover();
	}

	@Override
	public void previous() {
		pageIndex--;
		if (pageIndex < 1) {
			pageIndex = 1;
			return;
		}
		flipover();
	}
}
