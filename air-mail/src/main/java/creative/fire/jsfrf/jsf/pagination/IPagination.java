package creative.fire.jsfrf.jsf.pagination;

/**
 * @author feuyeux@gmail.com
 * @version 1.0
 */
public interface IPagination {
	int getPageIndex();

	int getPageSize();

	int getPageCount();

	int getTotalSize();

	boolean isDisabledFirst();

	boolean isDisabledLast();

	void first();

	void last();
	
	void previous();

	void next();

	void flipover();	
}