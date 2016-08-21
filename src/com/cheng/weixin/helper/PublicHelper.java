package com.cheng.weixin.helper;

import java.util.Collection;

public final class PublicHelper {

	public static boolean isEmpty(Collection<?> coll) {
        return ((null == coll) || (coll.isEmpty()));
    }
	
	public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }
}
