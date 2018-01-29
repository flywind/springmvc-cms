package com.uxdpen.cms.business.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.uxdpen.cms.business.common.constants.FBaseConstants;

/**
 * <p>
 * Base Util
 * 基础工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FBaseUtil {

	/**
	 * Split string based on the specified expression
	 * 根据指定表达式拆分字符串
	 * 
	 * @param content
	 *        Need to split the original string
	 * @param regex
	 *        Split expression
	 * @return
	 *        String array
	 */
	public static String[] split(String content, String regex) {
		String[] strArr = {};
		if (null == content || null == regex) {
			return strArr;
		}
		return content.split(regex);
	}
	
	/**
	 * Comma separated ID string, converted into List collection
	 * 将逗号分隔的id字符串，转换成List集合
	 * 
	 * @param ids
	 *        Comma separated ID string
	 * @return
	 *        List collection
	 */
	public static List<Long> idsToList(String ids) {
		List<Long> idList = new ArrayList<Long>();
		if (null != ids && ids.length() > 3) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				idList.add(Long.parseLong(id));
			}
		}
		return idList;
	}
	
	/**
	 * Converts the ID collection of the List type to a comma separated ID string
	 * 将List类型的id集合转换成以逗号分隔的id字符串
	 * 
	 * @param idList
	 *        ID type List collection
	 * @return
	 *        Comma separated ID string
	 */
	public static String listToIds(List<Long> idList) {
		StringBuilder ids = new StringBuilder();
		if (null != idList && idList.size() > 0) {
			for (Long id : idList) {
				ids.append(id).append(",");
			}
			return ids.substring(0, ids.length() - 1);
		}
		return FBaseConstants.NOT_EXIST;
	}
	
	/**
	 * Compare two strings are equal
	 * 比较两个字符串是否相等
	 * 
	 * @param txt1
	 *        String1
	 * @param txt2
	 *        String2
	 * @return
	 *        true:the same,false:not the same
	 */
	public static boolean equals(String txt1, String txt2) {
		if (txt1 == null && txt2 == null) {
			return true;
		} else if (txt1 != null && txt1.equals(txt2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Will edit the ID collection before editing and ID collection for comparison, confirmed the increase of ID
	 * 将编辑前的id集合与编辑后的id集合进行比较，确认出增加的id
	 * 
	 * @param newIds
	 *        new id collection
	 * @param oldIds
	 *        old id collection
	 * @return
	 *        Increased ID collection
	 */
	public static List<Long> addIds(List<Long> newIds, List<Long> oldIds) {
		List<Long> addIds = new ArrayList<Long>();
		for (Long newId : newIds) {
			if (!oldIds.contains(newId)) {
				addIds.add(newId);
			}
		}
		
		return addIds;
	}
	
	/**
	 * Will edit the ID collection before editing and ID collection for comparison, confirmed the reduction of ID
	 * 将编辑前的id集合与编辑后的id集合进行比较，确认出减少的id
	 * 
	 * @param newIds
	 *        new id collection
	 * @param oldIds
	 *        old id collection
	 * @return
	 *        Reduced ID collection
	 */
	public static List<Long> delIds(List<Long> newIds, List<Long> oldIds) {
		List<Long> delIds = new ArrayList<Long>();
		for (Long oldId : oldIds) {
			if (!newIds.contains(oldId)) {
				delIds.add(oldId);
			}
		}
		return delIds;
	}
	
	/**
	 * Generate six bit initial password
	 * 生成六位的初始密码
	 * 
	 * @return
	 *        Password string
	 */
	public static String generateInitPasword() {
		StringBuilder pwd = new StringBuilder();
		Random r = new Random();
		//Random number generated 0~9
		pwd.append(r.nextInt(9));
		//Generated a~z random number, 97 said a, 26 said the 26 letters
		pwd.append((char) (r.nextInt(26) + 97));
		pwd.append(r.nextInt(9));
		pwd.append((char) (r.nextInt(26) + 97));
		pwd.append(r.nextInt(9));
		pwd.append((char) (r.nextInt(26) + 97));
		return pwd.toString();
	}
	
	/**
	 * Generating a random number for a specified number of digits
	 * 生成一个指定位数的随机数
	 * 
	 * @param bit
	 *        Number of bits
	 * @return
	 *        Random number
	 */
	public static String generateRandomNumber(int bit) {
		StringBuilder pwd = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < bit; i++) {
			//产生0~9的随机数
			pwd.append(r.nextInt(9));
		}
		return pwd.toString();
	}
	
	/**
	 * Get the requested client IP
	 * 获取请求的客户端IP
	 * 
	 * @param req
	 *        request
	 * @return
	 *        IP
	 */
	public static String getClientIP(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
			ip = req.getHeader("Proxy-Client-IP");     
		}     
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
			ip = req.getHeader("WL-Proxy-Client-IP");     
		}     
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
			//Get IP is actually the address of the proxy server, and is not the real client IP address
			ip = req.getRemoteAddr(); 
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//Set a default IP
			ip = "127.0.0.1";
		}
		return ip;
	}
	
	public static String htmlTotext(String s) {
		if (StringUtils.isNotBlank(s)) {
			String str = s.replaceAll("<[.[^<]]*>", "");
			return str;
		}		
		return s;
	}
}
