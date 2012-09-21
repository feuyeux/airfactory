package creative.air.datastructure.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test HashMap
 * 1 mapï¼šé”®å€¼å¯¹ hashï¼šhashcode 
 * 2 é�žçº¿ç¨‹å®‰å…¨ é”®å€¼å…�è®¸ä¸ºç©º é”®ä¸ºç©ºæ—¶çš„å¤„ç�† 
 * 3 æ•°ç»„+é“¾è¡¨ç»“æž„ 
 * 4 è´Ÿè½½å› å­�loadFactorå’Œé˜ˆå€¼threshold æ‰©å®¹æœºåˆ¶ ç¼“å­˜è®¾è®¡ 
 * 5 é��åŽ†
 * 
 * Test Cache
 * @author
 * Eric Han feuyeux@gmail.com
 * 06/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class HashMapTest {
	static final Logger logger = Logger.getLogger(HashMapTest.class.getName());

	@Test
	public void test2() throws Exception {
		HashMapCache<String, Integer> cacheMap = new HashMapCache<String, Integer>();
		cacheMap.clear();
		cacheMap.put("" + 1, null);
		cacheMap.put(null, 1);
		cacheMap.put(null, null);
		Assert.assertNull(cacheMap.get(null));
	}

	@Test
	public void test5() {
		int n = 0;
		final int maxium = 5000;
		HashMapCache<String, Integer> cacheMap = new HashMapCache<String, Integer>(maxium);
		HashMapCache<String, Integer> retrievingMap = new HashMapCache<String, Integer>(maxium, maxium / 10, 0.5f);

		boolean inputRight = true;
		while (n < maxium) {
			String s = "" + n;
			try {
				cacheMap.put(s, n);
				retrievingMap.put(s, n++);
			} catch (Exception e) {
				e.printStackTrace();
				inputRight = false;
				break;
			}
		}
		Assert.assertTrue(inputRight);
		Object[] r1 = iterate(cacheMap, Level.INFO);
		Object[] r2 = iterate(retrievingMap, Level.INFO);
		logger.log(Level.INFO, "default map iterating elapse:{0}(start={1},end={2})", r1);
		logger.log(Level.INFO, "customize map iterating elapse:{0}(start={1},end={2})", r2);
		Assert.assertTrue((Long) r1[0] >= (Long) r2[0]);
	}

	/**
	 * @param level 
	 * 
	 */
	private Object[] iterate(HashMapCache<String, Integer> map, Level level) {
		Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
		long startTime = System.currentTimeMillis();
		while (iter.hasNext()) {
			Map.Entry<String, Integer> entry = iter.next();
			String key = entry.getKey();
			Integer val = entry.getValue();
			logger.log(level, "{0}:{1}", new Object[] { key, val });
		}
		long endTime = System.currentTimeMillis();
		return new Object[] { endTime - startTime, endTime, startTime };
	}
	
	@Test
	public void testPutAll(){
		HashMap<String,String> map=new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> map2=new HashMap<String,HashMap<String,String>>();
		
		map.put("Gateway", "Thomson");
		map2.put("patner", map);
		map.put("Gateway", "Technicolor");
		
		Iterator<Map.Entry<String,HashMap<String,String>>> iter = map2.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,HashMap<String,String>> entry = iter.next();
			String key = entry.getKey();
			HashMap<String,String> val = entry.getValue();
			
			Iterator<Map.Entry<String,String>> iter2 = val.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String,String> entry2 = iter2.next();
				String key2 = entry2.getKey();
				String val2 = entry2.getValue();
				Assert.assertEquals("Technicolor", val2);
				logger.log(Level.INFO, "{0}:{1}", new Object[] { key2, val2 });
			}
			logger.log(Level.INFO, "{0}:{1}", new Object[] { key, val });
		}
	}
}
