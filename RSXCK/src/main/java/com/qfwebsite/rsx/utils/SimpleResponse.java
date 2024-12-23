package com.qfwebsite.rsx.utils;

/**
 * Created by arthur on 16/6/30.
 */
public class SimpleResponse<T>
{

	private int code;
	private String message;
	private T data;

	public SimpleResponse()
	{
	}

	public SimpleResponse(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public SimpleResponse(int code, String message, T data)
	{
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> SimpleResponse<T> ok(T data)
	{
		return new SimpleResponse<T>(HttpCode.SUCCESS, "OK", data);
	}

	public static <T> SimpleResponse<T> error(int code, String message)
	{
		return new SimpleResponse<T>(code, message);
	}

	public static <T> SimpleResponse<T> innerError() {
		return new SimpleResponse<T>(HttpCode.INNER_ERROR, "INNER ERROR");
	}

	public static <T> SimpleResponse<T> timeout() {
		return new SimpleResponse<T>(HttpCode.TIME_OUT, "TIME OUT");
	}

	public boolean responseOk()
	{
		return this.code == HttpCode.SUCCESS;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}
}
