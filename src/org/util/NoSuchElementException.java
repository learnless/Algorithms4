package org.util;

/**
 * 自定义异常类，元素不存在
 * @author Administrator
 *
 */
public class NoSuchElementException extends RuntimeException {

	private static final long serialVersionUID = -755933448322680742L;

	public NoSuchElementException() {
		super();
	}

	public NoSuchElementException(String message) {
		super(message);
	}
	
}
