package creative.air.datastructure.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 06/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class HashMapCache<H, L> {
	enum ConcurrentStrategy {
		NOTIFY, WAIT, TIMEOUT
	}

	enum FullStrategy {
		NOTIFY, DISCARD, LRU
	}

	static final Logger logger = Logger.getLogger(HashMapCache.class.getName());
	//cache full strategy
	private boolean replace = true;
	private int capacity = 12;
	private FullStrategy fullStrategy = FullStrategy.NOTIFY;

	//cache lock strategy
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static WriteLock wLock = lock.writeLock();
	private static ReadLock rLock = lock.readLock();
	private ConcurrentStrategy concurrentStrategy = ConcurrentStrategy.NOTIFY;

	private HashMap<H, L> map;

	public HashMapCache() {
		map = new HashMap<H, L>();
	}

	public HashMapCache(int capacity) {
		this.capacity = capacity;
		map = new HashMap<H, L>();
	}

	public HashMapCache(int capacity, int initialCapacity, float loadFactor) {
		this.capacity = capacity;
		map = new HashMap<H, L>(initialCapacity, loadFactor);
	}

	public void clear() {
		try {
			wLock.lock();
			map.clear();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "clear error", e);
		} finally {
			wLock.unlock();
		}

	}

	public Set<Map.Entry<H, L>> entrySet() {
		return map.entrySet();
	}

	public L get(H key) {
		try {
			rLock.lock();//tryLock(long timeout, TimeUnit unit)
			return map.get(key);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "get error", e);
			return null;
		} finally {
			rLock.unlock();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public ConcurrentStrategy getConcurrentStrategy() {
		return concurrentStrategy;
	}

	public FullStrategy getFullStrategy() {
		return fullStrategy;
	}

	public boolean isReplace() {
		return replace;
	}

	public Iterator<Map.Entry<H, L>> iterate() {
		return map.entrySet().iterator();
	}


	public L put(H key, L value) throws Exception {
		wLock.lock();

		if (this.capacity < map.size()) {
			throw new Exception("reached the cache's maximum");
		}
		try {
			if (this.replace) {
				return map.put(key, value);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "put error", e);
			return null;
		} finally {
			wLock.unlock();
		}
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setConcurrentStrategy(ConcurrentStrategy concurrentStrategy) {
		this.concurrentStrategy = concurrentStrategy;
	}

	public void setFullStrategy(FullStrategy fullStrategy) {
		this.fullStrategy = fullStrategy;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}
}
