package com.leaf.base.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsonUtil {
	/**
	 * 将List对象转换为JSONArray对象并输出
	 * 
	 * @param list
	 * @param response
	 */
	public static void outJSONArray(List<?> list, HttpServletResponse response) {
		JSONArray jsonArray = null;
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (null != list && 0 < list.size()) {
				jsonArray = JSONArray.fromObject(list);
			}
			out.print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 将一个对象转换成JSONObject对象并输出
	 * 
	 * @param object
	 * @param callback
	 * @param response
	 */
	public static void outJSONObject(Object object, String callback, HttpServletResponse response) {
		JSONObject json = null;
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (null != object) {
				if (object instanceof JSONObject) {
					json=(JSONObject)object;
				}else{
					json = JSONObject.fromObject(object);
				}
				if (callback != null && !"".equals(callback)) {
					out.write(callback + "(" + json + ")");
				} else {
					out.print(json);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 输出JSONObject对象
	 * 
	 * @param json
	 * @param callback
	 * @param response
	 */
	// public static void outJSONObject(JSONObject json, String callback,
	// HttpServletResponse response) {
	// response.setContentType("text/html;charset=utf-8");
	// String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
	// JSONUtils.getMorpherRegistry().registerMorpher(new
	// DateMorpher(dateFormats));
	// PrintWriter out = null;
	// try {
	// out = response.getWriter();
	// if (null != json) {
	// if (callback != null && !"".equals(callback)) {
	// out.write(callback + "(" + json + ")");
	// } else {
	// out.print(json);
	// }
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (null != out) {
	// out.flush();
	// out.close();
	// }
	// }
	// }

	/**
	 * 将json转化为实体POJO
	 * 
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	public static <T> Object JSONToObj(String jsonStr, Class<T> obj) {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将实体POJO转化为JSON字符串
	 * 
	 * @param obj
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static <T> JSONObject objectToJson(T obj) throws JSONException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw e;
		}

		return JSONObject.fromObject(jsonStr);
	}

}
