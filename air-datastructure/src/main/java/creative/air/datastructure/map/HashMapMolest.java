package creative.air.datastructure.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HashMap 1 map：键值对 hash：hashcode 2 非线程安全 键值允许为空 键为空时的处理 3 数组+链表结构 4 负载因子和阀值
 * 扩容机制 缓存设计 5 遍历
 *
 * @author hanl
 */
public class HashMapMolest {

    HashMap<String, Integer> hmap = new HashMap<String, Integer>();
    HashMap<String, Integer> hmap2 = new HashMap<String, Integer>(1000, 0.5f);
    static final Logger logger = Logger.getLogger(HashMapMolest.class.getName());

    public void test4() {

        int n = 0;
        while (n < 50000) {
            hmap.put("" + n, n);
            hmap2.put("" + n, n++);
        }
    }

    public void test5() {
        Iterator<Map.Entry<String, Integer>> iter;
        iter = hmap.entrySet().iterator();
        long s1 = System.currentTimeMillis();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            String key = entry.getKey();
            Integer val = entry.getValue();
            logger.log(Level.FINE, "{0}:{1}", new Object[]{key, val});
        }
        long e1 = System.currentTimeMillis();

        Iterator<Map.Entry<String, Integer>> iter2;
        iter2 = hmap2.entrySet().iterator();
        long s2 = System.currentTimeMillis();
        while (iter2.hasNext()) {
            Map.Entry<String, Integer> entry = iter2.next();
            String key = entry.getKey();
            Integer val = entry.getValue();
            logger.log(Level.FINE, "{0}:{1}", new Object[]{key, val});
        }
        long e2 = System.currentTimeMillis();
        logger.log(Level.INFO, "default map iterating elapse:{0}(start={1},end={2})", new Object[]{e1 - s1, e1, s1});
        logger.log(Level.INFO, "customize map iterating elapse:{0}(start={1},end={2})", new Object[]{e2 - s2, e2, s2});
    }

    public static void main(String[] ss) {
        HashMapMolest testHashMap = new HashMapMolest();
        testHashMap.test4();
        testHashMap.test5();
    }
}
