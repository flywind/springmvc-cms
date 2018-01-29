package com.uxdpen.cms.business.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.alibaba.fastjson.JSON;

/**
 * <p>JSON Serializer</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class JSONSerializer {

	/**
	 * Default charset is UTF-8
	 */
	private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * To string
     * @param object
     * 		  Entity
     * @return
     * 		  String
     */
    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * To object
     * @param string
     * 			Json string
     * @param clz
     * 			Entity class
     * @return
     * 			Entity
     */
    public static <T> T deserialize(String string, Class<T> clz) {
        return JSON.parseObject(string, clz);
    }

    public static <T> T load(Path path, Class<T> clz) throws IOException {
        return deserialize(
                new String(Files.readAllBytes(path), DEFAULT_CHARSET_NAME), clz);
    }

    public static <T> void save(Path path, T object) throws IOException {
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        Files.write(path,
                serialize(object).getBytes(DEFAULT_CHARSET_NAME),
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
