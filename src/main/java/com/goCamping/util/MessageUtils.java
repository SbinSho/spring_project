package com.goCamping.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {
	
	private static MessageSourceAccessor accessor = null;

	public static void setAccessor(MessageSourceAccessor accessor) {
		MessageUtils.accessor = accessor;
	}

	public static String getMessage(String code) {
		return accessor.getMessage(code, Locale.getDefault());
	}
	
	public static String getMessage(String code, Object[] objs) {
		return accessor.getMessage(code, objs, Locale.getDefault());
	}
}
