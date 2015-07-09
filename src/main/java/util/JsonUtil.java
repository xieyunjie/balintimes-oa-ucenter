package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonUtil {
    private static ObjectMapper objectMapper;

    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <T> T ToObject(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();

            objectMapper.setDateFormat(dateFormat);

            SimpleModule module = new SimpleModule();
            module.addSerializer(String.class, new StringUnicodeSerializer());
            objectMapper.registerModule(module);
        }

        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String ToJson(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(dateFormat);

            SimpleModule module = new SimpleModule();
            module.addSerializer(String.class, new StringUnicodeSerializer());
            objectMapper.registerModule(module);
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String ResponseSuccessfulMessage(Object data) {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setData(data);
        return JsonUtil.ToJson(jsonResponseMsg);
    }

    public static String ResponseSuccessfulMessage(Object data, int total) {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setData(data);
        jsonResponseMsg.setTotal(total);
        // jsonResponseMsg.setPageSize(pageSize);
        return JsonUtil.ToJson(jsonResponseMsg);
    }

    public static String ResponseSuccessfulMessage() {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        return JsonUtil.ToJson(jsonResponseMsg);
    }

    public static String ResponseSuccessfulMessage(String message) {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setResponseMsg(message);
        return JsonUtil.ToJson(jsonResponseMsg);
    }

    public static String ResponseFailureMessage() {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setSuccess("false");
        return JsonUtil.ToJson(jsonResponseMsg);
    }

    /*返回一个失败的信息*/
    public static String ResponseFailureMessage(String message) {
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setSuccess("false");
        jsonResponseMsg.setResponseMsg(message);
        return JsonUtil.ToJson(jsonResponseMsg);
    }


    public static String ResponsePermissionMessage(boolean ispermission, String message) {
        
        JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
        jsonResponseMsg.setPermission(ispermission);
        jsonResponseMsg.setResponseMsg(message);

        return JsonUtil.ToJson(jsonResponseMsg);

    }


}
