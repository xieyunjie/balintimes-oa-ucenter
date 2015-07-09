package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * Created by AlexXie on 2015/7/20.
 */
public class LogUtil {


    private static Logger dbLog = LoggerFactory.getLogger("LogUtil.DBInfo");
    private static Logger consoleLog = LoggerFactory.getLogger("LogUtil.ConsoleInfo");


    public static void ConsoleInfo(String msg) {

        consoleLog.info(msg);
    }


    public static void DBInfo(String msg, String method, String action, Object... params) {

        MDC.put("Exception", "Exception");
        MDC.put("Action", action);

        MDC.put("Method", method);
        MDC.put("ClientIp", "ClientIp");
        MDC.put("RequestUrl", "RequestUrl");
        MDC.put("AppUid", "AppUid");
        MDC.put("AppName", "AppName");
        MDC.put("UserUid", "UserUid");
        MDC.put("UserName", "UserName");
        MDC.put("EmpUid", "EmpUid");
        MDC.put("EmpName", "EmpName");
        MDC.put("Params", JsonUtil.ToJson(params));
        dbLog.info(msg);
        MDC.clear();

    }
}
