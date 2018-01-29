package com.uxdpen.cms.business.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.entities.base.FBase;

/**
 * <p>System util</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FSysUtil {
	
	public static String listToStrs(List<String> objList) {
		StringBuilder strs = new StringBuilder();
		if (null != objList && objList.size() > 0) {
			for (String obj : objList) {
				strs.append(obj).append(",");
			}
			return strs.substring(0, strs.length() - 1);
		}
		return null;
	}
	
	public static List<String> strsToList(String strs,String separator){
		
		if(null == separator){
			separator = ",";
		}
		
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(strs)){
			String[] ss = strs.split(separator);
			for(String s : ss){
				list.add(s);
			}
		}
		
		return list;
	}
	
	/**
	 * 检查字符串在集合中是否已经在在
	 * 
	 * @param str
	 * @param strList
	 * @return
	 * 		True:exists, false:does not exist
	 */
	public static boolean strExist(String str, List<String> strList){
		if(null != strList){
			if(strList.contains(str)){
				return true;
			}else{
				strList.add(str);
			}
			
		}
		return false;
	}
	
	/**
	 * Check if Id is already in the collection.
	 * 检查id在集合中是否已经在在
	 * 
	 * @param id
	 *        id
	 * @param idList
	 *        id list
	 * @return
	 *        True:exists, false:does not exist
	 */
	public static boolean idExist(Long id, List<Long> idList) {
		if (null != idList) {
			if (idList.contains(id)) {
				return true;
			} else {
				idList.add(id);
			}
		}
		return false;
	}
	
	/**
	 * Generation protocol version number
	 * 生成协议版本号
	 * 
	 * @return
	 *        version
	 */
	public static String getProtocolVersion() {
		//Using UUID to ensure that the protocol version number is unique
		String uuid = UUID.randomUUID().toString();
		String value = DigestUtils.md5Hex(uuid);
		
		//MD5 encryption to take the middle of the 16
		String version = value.substring(8, 24);
		return version;
	}
	
	/**
	 * The decimal integer into the specified number of bytes of binary number, inadequate in the high fill 0
	 * 将十进制的整数转换成指定字节位数的二进制数，不足时在高位补0
	 * 
	 * @param value
	 *        Decimal integer
	 * @param byteLength
	 *        Number of bytes
	 * @return
	 *        Binary string
	 */
	public static String intToBinary(int value, int byteLength) {
		String bval = Integer.toBinaryString(value);
		//1 bytes equal to 8 bits
		int len = byteLength * 8;
		if (bval.length() < len) {
			StringBuilder zeros = new StringBuilder();
			int count = len - bval.length();
			for (int i = 0; i < count; i++) {
				zeros.append('0');
			}
			bval = zeros.toString() + bval;
		}
		return bval;
	}
	
	/**
	 * The decimal integer into the specified number of bytes of binary number, inadequate in the high fill 0
	 * 将十进制的整数转换成指定字节位数的二进制数，不足时在高位补0
	 * 
	 * @param value
	 *        true:1，false:0
	 * @param byteLength
	 *        Number of bytes
	 * @return
	 *        Binary string
	 */
	public static String intToBinary(boolean value, int byteLength) {
		if (value) {
			return intToBinary(1, byteLength);
		}
		return intToBinary(0, byteLength);
	}
	
	/**
	 * Converts an object of a List type to a comma separated ID string
	 * 将List类型的对象集合转换成以逗号分隔的id字符串
	 * 
	 * @param objList
	 *        Inherited from the List class FBase collection
	 * @return
	 *        Comma separated ID string
	 */
	public static String listToIds(List<? extends FBase> objList) {
		StringBuilder ids = new StringBuilder();
		List<Long> idList = new ArrayList<Long>();
		if (null != objList && objList.size() > 0) {
			for (FBase obj : objList) {
				if (idList.contains(obj.getId())) {
					continue;
				}
				idList.add(obj.getId());
				ids.append(obj.getId()).append(",");
			}
			return ids.substring(0, ids.length() - 1);
		}
		return FBaseConstants.NOT_EXIST;
	}
	
	/**
	 * Get international resource information
	 * 获取国际化资源信息
	 * 
	 * @param gridFieldMap
	 *        Map of resource information
	 * @param key
	 *        Resuorce key
	 * @return
	 *        Resuorce value
	 */
	public static String getResVal(Map<String,Object> gridFieldMap, String key) {
		String info = FBaseConstants.EMPTY_STRING;
		if (null != gridFieldMap) {
			Object val = gridFieldMap.get(key);
			if (null != val) {
				info = val.toString();
			}
		}
		return info;
	}
	
	/**
	 * A plurality of string "- >" separated.
	 * 将多个字符串用"-->"隔开。
	 * 
	 * @param args
	 *        String group
	 * @return
	 *        With "-->" separated's String
	 */
	public static String generateArrowValue(String... args) {
		if (null != args) {
			StringBuilder value = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				String v = args[i];
				if (value.length() > 0) {
					value.append(" ").append("-->").append(" ");
				}
				value.append(v);
			}
			return value.toString();
		}
		return FBaseConstants.EMPTY_STRING;
	}
	
	/**
	 * The string 16 MD5 encryption, support for one or more of the string of a combination of encryption
	 * 将字符串进行16位 MD5加密，支持一个或多个字符串的组合加密
	 * 
	 * @param args
	 *        String array
	 * @return
	 *        Encrypted MD5 string
	 */
	public static String generateMd5Key(String... args) {
		if (null != args) {
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				str.append(args[i]);
			}
			return DigestUtils.md5Hex(str.toString()).substring(8, 24);
		}
		return FBaseConstants.EMPTY_STRING;
	}
	
	/**
	 * Convert Object type to Integer type
	 * 将Object类型转换成Integer类型
	 * 
	 * @param obj
	 *        Object
	 * @return
	 *        Returns 0 when Object is invalid
	 */
	public static int objectToInt(Object obj) {
		try {
			return (int) obj;
		} catch (Exception e) {
			return 0;
		}
	}

}
