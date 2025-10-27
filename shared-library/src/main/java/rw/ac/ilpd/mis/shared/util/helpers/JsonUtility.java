package rw.ac.ilpd.mis.shared.util.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 14/07/2025
 */
public class JsonUtility {

    public static final Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    public static <T> String stringify(T object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse object into json : "+object.toString());
            logger.error(e.getStackTrace().toString());
        }
        return jsonStr;
    }

    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        T object = null;
        try {
            object = mapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unable to parse json string into object : "+jsonStr);
            logger.error(e.getStackTrace().toString());
        }
        return object;
    }
}
