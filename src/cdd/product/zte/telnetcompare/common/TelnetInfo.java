package cdd.product.zte.telnetcompare.common;

import java.io.Serializable;

public class TelnetInfo implements Serializable
{
    private static final long serialVersionUID = 5447328899713501711L;
    
    public String ipAddr;
    public String prompt;
    public String pageSplitString;
    public String loginPrompt;
    public String passwdPrompt;
    public String loginUserName;
    public String loginPasswd;
}
