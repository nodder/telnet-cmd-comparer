package cdd.product.zte.telnetcompare.bussiness;

import java.io.IOException;

import cdd.product.zte.telnetcompare.common.TelnetInfo;
import cdd.product.zte.telnetcompare.common.TelnetSession;


public class RunnConfgTelnetSession
{
    private static final String CMD = "show running-config";
    
    public String runCmd(TelnetInfo telnetInfo) throws IOException
    {
        TelnetSession session = new TelnetSession();

        StringBuffer output = new StringBuffer();
        try
        {
            session.connect(telnetInfo.ipAddr);
            session.setSocketTimeout(5000);
            session.send("\n");
            session.setLoginPrompt(telnetInfo.loginPrompt);
            session.setPasswdPrompt(telnetInfo.passwdPrompt);
            session.login(telnetInfo.loginUserName, telnetInfo.loginPasswd);
            session.setPromptList(new String[]
                {telnetInfo.prompt, telnetInfo.pageSplitString});

            sleep(800);

            String response = session.send(CMD);
            while(response.contains(telnetInfo.pageSplitString))
            {
                output.append(filterString(response));
                response = session.send("");
            }

            output.append(response);
        }
        finally
        {
            session.disconnect();
        }
        
        System.out.println("All Over!");
        return output.toString();
    }

    private String filterString(String response)
    {
        String filterSpliterStr = response.substring(0, response.lastIndexOf("\r\n"));//IAP and MAP

        if(filterSpliterStr.indexOf('\b') >= 0)//IAP
        {
            String filterBackspace = filterSpliterStr.replace("\b", "");
            return filterBackspace + "\r\n";
        }
        else
        {
            return filterSpliterStr;
        }
    }

    private void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
