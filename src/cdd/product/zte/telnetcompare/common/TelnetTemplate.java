package cdd.product.zte.telnetcompare.common;

import java.util.LinkedHashMap;
import java.util.Map;


public class TelnetTemplate
{
    public static Map<String, TelnetInfo> getTemplateMap()
    {
        Map<String, TelnetInfo> map = new LinkedHashMap<String, TelnetInfo>();
        map.put("MAP Standard", getTelnetInfoForMap());
        map.put("IAP Standard", getTelnetInfoForIap());
//        map.put("MAP_9800 Enhanced", getTelnetInfoForMapAnd9800Enhance());
        
        return map;
    }
    
    private static TelnetInfo getTelnetInfoForIap()
    {
        TelnetInfo info = new TelnetInfo();
        
        info.loginPrompt = "Username:";
        info.loginUserName = "zte";
        info.passwdPrompt = "Password:";
        info.loginPasswd = "zte";
        info.prompt = "ZXAN#";
        info.pageSplitString = " --More--";
        
        return info;
    }

    private static TelnetInfo getTelnetInfoForMap()
    {
        TelnetInfo info = new TelnetInfo();
            
        info.loginPrompt = "Login:";
        info.loginUserName = "admin";
        info.passwdPrompt = "Password:";
        info.loginPasswd = "admin";
        info.prompt = "KPN>";
        info.pageSplitString = "Press any key to continue (Q to quit)";
        
        return info;
    }
    
//    private static TelnetInfo getTelnetInfoForMapAnd9800Enhance()
//    {
//        TelnetInfo info = new TelnetInfo();
//        
//        info.loginPrompt = "Login:";
//        info.loginUserName = "#B&*(TRt%Edf$JN";
//        info.passwdPrompt = "Password:";
//        info.loginPasswd = "`-sAwII)_3lk2S";
//        info.prompt = "ZXAN#";
//        info.pageSplitString = "Press any key to continue (Q to quit)";
//        
//        return info;
//    }
}
