package net.myscloud.open.yuna.common.kits;

import java.util.Collection;
import java.util.Map;

/**
 * Created by genesis on 15/11/26.
 */
public class CollectionKits {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    private CollectionKits() {
    }
}
