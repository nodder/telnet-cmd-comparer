package cdd.product.zte.telnetcompare.common;

import java.io.IOException;
import java.net.SocketException;

import de.mud.telnet.TelnetWrapper;

/**
 * <p>文件名称: TelnetSession.java</p>
 * <p>文件描述: 封装telnet协议</p>
 * <p>版权所有: 版权所有(C)2001-2004</p>
 * <p>公    司: 中兴通讯股份有限公司</p>
 * <p>内容摘要: </p>
 * <p>其他说明: 要求有JTA包，用完后请务必disconnet()</p>
 * <p>完成日期：2004年5月26日</p>
 * <p>修改记录1:</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：</p>
 * @version 1.0
 * @author  jxj
 *<b> <p>应用举例: </p></b>
 *
 *      TelnetSession telnet = new TelnetSession();<br>
       telnet.setPrompt("#");<br>
       try<br>
       {<br>
        &nbsp;&nbsp;&nbsp;    telnet.connect("10.61.94.23", 23);<br>
        &nbsp;&nbsp;&nbsp;    telnet.setSocketTimeout(5000);<br>
        &nbsp;&nbsp;&nbsp;    telnet.setLoginPrompt("Login:");<br>
        &nbsp;&nbsp;&nbsp;    telnet.setPasswdPrompt("Password:");<br>
        &nbsp;&nbsp;&nbsp;    telnet.login("su3", "su3");<br>
        &nbsp;&nbsp;&nbsp;    telnet.send("show");<br>
        &nbsp;&nbsp;&nbsp;    telnet.setPrompt("(show)#");<br>
        &nbsp;&nbsp;&nbsp;    telnet.send("hw all");<br>
       }<br>
       catch (Exception ex)<br>
       {<br>
        &nbsp;&nbsp;&nbsp;    ex.printStackTrace();<br>
       }<br>
       try<br>
       {<br>
        &nbsp;&nbsp;&nbsp;    telnet.disconnect();// 保证断开连接<br>
       }<br>
       catch (Exception ex)<br>
       {<br>
       }<br>
 */

public class TelnetSession extends TelnetWrapper
{
    public TelnetSession()
    {
//        isLogin = false;
        loginPrompt = "login:";
        passwdPrompt = "Password:";
        prompt = null;
        loginMessage = null;
        promptStrings = null;
        partialData = null;
        cmdEcho = true;
        promptEcho = true;
        readBufferLength = 256;
        cmdSuffix = "\r\n";
    }

    /**
     * 功能：连接telnet服务器，端口号为默认23
     * @param remoteHost String telnet服务器地址
     * @throws IOException
     */
    public void connect(String remoteHost) throws IOException
    {
        super.connect(remoteHost, 23);
    }

    /**
     * 功能：连接telnet服务器
     * @param remoteHost String telnet服务器地址
     * @param remotePort int 端口号
     * @throws IOException
     * This will log in with device with a particular login name and password.
     * The login is performed based on the login and password parameter. Some
     * devices prompt directly for a password without the user name. For such
     * devices the login method can be called as follows: <br>
     *  String login = ""; // or null<br>
     *  String password = "test"; // assuming test is the password<br>
     *  login(login,password); // perform the login<br>
     */
    public void connect(String remoteHost, int remotePort) throws IOException
    {
        super.connect(remoteHost, remotePort);
    }

    static void debugPrint(String s)
    {
        if(debugFlag)
        {
            String s1 = "DEBUG :";
            String s2 = "\n";
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s1)))).
                append(s).append(s2))));
        }
    }

    public String getCommandSuffix()
    {
        return cmdSuffix;
    }

    /**
     * 获得登录信息，但实际测试发现有问题
     * Gets the initial login message after connecting to the device.
     * @return String
     * @deprecated
     */
    public String getLoginMessage()
    {
        return loginMessage;
    }

    public String getLoginPrompt()
    {
        return loginPrompt;
    }

    /**
     * Gets the partialData read. This is the data that is read until a time-out
     * occurs. Time-out occurs if the expected prompt is not sent by the device.
     * @return String
     */
    public String getPartialData()
    {
        return partialData;
    }

    public String getPasswdPrompt()
    {
        return passwdPrompt;
    }

    public String getPrompt()
    {
        return prompt;
    }

    /**
     * To get the prompt list. This is the list of prompts one of which will be
     * returned by the device after execution of a command.
     * @return String[]
     */
    public String[] getPromptList()
    {
        return promptStrings;
    }

    public int getReadBufferLength()
    {
        return readBufferLength;
    }

    public int getSocketTimeout() throws SocketException
    {
        return socket.getSoTimeout();
    }

    /**
     * Sets the command echo flag to enable/disable the echoing of commands.<br>
     * 默认为true
     * @return boolean
     */
    public boolean isSetCommandEcho()
    {
        return cmdEcho;
    }

    public boolean isSetDebug()
    {
        return debugFlag;
    }

    /**
     * Sets the prompt echo flag to enable/disable the echoing of prompts
     */
    public boolean isSetPromptEcho()
    {
        return promptEcho;
    }

    /**
     * 功能：登录
     * @param userName String 如果用户名为空,则不输入用户名
     * @param password String 如果密码为空,则不输入密码
     * @throws IOException
     */
    public void login(String userName, String password) throws IOException
    {
//        this.isLogin = true;
        if((userName == null || userName.length() == 0) && (password == null || password.length() == 0))
        {
            loginMessage = waitfor(prompt);
        }
        else
        if(userName != null && userName.length() > 0 && (password == null || password.length() == 0))
        {
            waitfor(loginPrompt);
            loginMessage = send(userName, prompt);
        }
        else
        if(password != null && password.length() > 0 && (userName == null || userName.length() == 0))
        {
            waitfor(passwdPrompt);
            loginMessage = send(password, prompt);
        }
        else
        {
            waitfor(loginPrompt);
            send(userName, passwdPrompt);

            loginMessage = send(password, prompt);
            if(this.isSetDebug())
            {
                System.out.print(loginMessage);
            }
        }
    }

    /**
     * 功能：发送命令,并返回执行结果
     * @param command String 命令
     * @throws IOException
     * @return String 命令执行结果 如果没有设置提示符,则返回为空
     */
    public String send(String command) throws IOException
    {
        return send(command, prompt);
    }

    private String send(String command, String prompt) throws IOException
    {
        lastCommand = command;
        in.skip(in.available());
        if(cmdSuffix != null)
        {
            byte bs[] = (String.valueOf(command) + String.valueOf(cmdSuffix)).getBytes();
            write(String.valueOf(command) + String.valueOf(cmdSuffix));
        }
        else
        {
            write(command);
        }
        debugPrint("Message sent:".concat(String.valueOf(String.valueOf(command))));
        if(promptStrings != null)
        {
            return waitfor(promptStrings);
        }
        if(prompt != null)
        {
            return waitfor(prompt);
        }
        else
        {
            return null;
        }
    }

    private void write(String s) throws IOException
    {
        byte[] ab = s.getBytes();
        write(ab);
    }

    public void setCommandEcho(boolean flag)
    {
        cmdEcho = flag;
    }

    /**
     * Sets the command Suffix that will be appended to the command. This is the
     * string that will be appended to the end of each command sent
     * @param s String
     */
    public void setCommandSuffix(String s)
    {
        cmdSuffix = s;
    }

    /**
     * 设置是否调试
     * @param flag boolean
     */
    public void setDebug(boolean flag)
    {
        debugFlag = flag;
    }

    public void setLoginPrompt(String s)
    {
        loginPrompt = s;
    }

    public void setPasswdPrompt(String s)
    {
        passwdPrompt = s;
    }

    public void setPrompt(String s)
    {
        prompt = s;
    }

    public void setPromptEcho(boolean flag)
    {
        promptEcho = flag;
    }

    public void setPromptList(String as[])
    {
        promptStrings = as;
    }

    public void setReadBufferLength(int i)
    {
        if(i > 256)
        {
            readBufferLength = i;
        }
    }

    /**
     * 设置socket超时，单位ms
     * @param i int 超时
     * @throws SocketException
     */
    public void setSocketTimeout(int i) throws SocketException
    {
        socket.setSoTimeout(i);
    }

    String truncateResponse(String s)
    {
        String s1 = s;
        if(!cmdEcho && lastCommand != null && s.startsWith(lastCommand))
        {
            s1 = s1.substring(lastCommand.length());
        }
        if(!promptEcho && prompt != null && s.endsWith(prompt))
        {
            s1 = s1.substring(0, s1.length() - prompt.length());
        }
        return s1;
    }

    /*
     * 从服务器返回信息中等待字符串<br>
     * Waits for a string to come from the remote host and returns all those
     * characters which are received until that happens (including the string
     * being waited for). Times out in case the expected string were not
     * received. The partial data read can be obtained using getPartialData().
     * This will return the data that was read until the time-out.
     * @param strWait String 被等待的字符串
     * @throws IOException
     * @return String 响应信息
     * 这个函数测试发现有问题,所以注释掉,直接适用父类中的实现
     */
    /*
         public String waitfor(String strWait) throws IOException
         {
        scriptHandler.setup(strWait);
        byte abyte0[] = new byte[readBufferLength];
        byte abyte1[] = new byte[readBufferLength + 1];
        int i = 0;
        String strResponse = "";
        debugPrint("Parameter to match:" + strWait);
        byte b0 = 0;
        byte b1 = 0;
        do
        {
            if (i < 0)
            {
                break;
            }
            try
            {
                i = read(abyte0);
                String str;
                if (i == readBufferLength && abyte0[i - 1] < 0)
                {
                    str = new String(abyte0, 0, i - 1);
                }
                else
                {
                    str = new String(abyte0, 0, i);
                }
//                debugPrint("String.length: ".concat(String.valueOf(String.valueOf(str.length()))));
                System.out.println("read for server:\n" + str);
                debugPrint("Message rcvd:"+ str);
            }
            catch (InterruptedIOException interruptedioexception)
            {
                if(this.isSetDebug())
                {
                    interruptedioexception.printStackTrace();
                }
                if (i > 0)
                {
                    if (i == readBufferLength && abyte0[i - 1] < 0)
                    {
                        strResponse = strResponse + String.valueOf(new String(abyte0, 0, i - 1));
                    }
                    else
                    {
                        strResponse = strResponse + String.valueOf(new String(abyte0, 0, i));
                    }
                }
                if (!cmdEcho || !promptEcho)
                {
                    strResponse = truncateResponse(strResponse);
                }
                partialData = strResponse;
                throw interruptedioexception;
            }
            if (i > 0)
            {
                if (i == readBufferLength && abyte0[i - 1] < 0)
                {
                    int x = 0;
                    for (int j = readBufferLength - 1; j >= 0 && abyte0[j] < 0; j--)
                    {
                        x = 1 - x;

                    }
                    if (x == 1)
                    {
                        b1 = abyte0[i - 1];
                        abyte0[i - 1] = 0;
                        i--;
                    }
                }
                byte abyte[];
                if (b0 < 0)
                {
                    for (int j = 0; j < i; j++)
                    {
                        abyte1[j + 1] = abyte0[j];

                    }
                    abyte1[0] = b0;
                    abyte = abyte1;
                    i++;
                }
                else
                {
                    abyte = abyte0;
                }
                b0 = b1;
                b1 = 0;
                strResponse = strResponse + String.valueOf(new String(abyte, 0, i));
                if (scriptHandler.match(abyte, i))
                {
                    debugPrint("Parameter match SUCCESSFUL:" + strWait);
                    partialData = null;
                    int iRead = socket.getInputStream().available();
                    if ( iRead <= 0)
                    {//输入流中没有字符了
                        if (!cmdEcho || !promptEcho)
                        {
                            strResponse = truncateResponse(strResponse);
                        }
                        return strResponse;
                    }
                    else
                    {
//                        strResponse = "";
                        if(!this.isLogin)
                        {// 下面这个发送回车的命令很奇怪，不知道什么情况会执行到这里
                            this.debugPrint("发送回车");
                            simpleSend(cmdSuffix);
                        }
                    }
                }
            }
        } while (true);
        return null;
         }
// */

    /**
     * Waits for strings to come from the remote host and returns all those
     * characters which are received until that happens (including the string
     * being waited for). This occurs when any one of the prompt in the list is
     * received. Times out in case the expected strings were not received. The
     * partial data read can be obtained using getPartialData(). This will
     * return the data that was read until the time-out.
     * @param as String[] the strings to look for
     * @throws IOException
     * @return String
     */
//    public String waitfor(String as[]) throws IOException
//    {
//        int i = as.length;
//        byte abyte0[] = new byte[readBufferLength];
//        int j = 0;
//        String s = "";
//        do
//        {
//            if (j < 0)
//            {
//                break;
//            }
//            try
//            {
//                j = read(abyte0);
//            }
//            catch (InterruptedIOException interruptedioexception)
//            {
//                if (j > 0)
//                {
//                    s = String.valueOf(s) + String.valueOf(new String(abyte0, 0, j));
//                }
//                if (!cmdEcho || !promptEcho)
//                {
//                    s = truncateResponse(s);
//                }
//                partialData = s;
//                throw new IOException(interruptedioexception.getMessage());
//            }
//            if (j > 0)
//            {
//                s = String.valueOf(s) + String.valueOf(new String(abyte0, 0, j));
//                int k = 0;
//                while (k < i)
//                {
//                    scriptHandler.setup(as[k]);
//                    if (scriptHandler.match(abyte0, j))
//                    {
//                        partialData = null;
//                        if (!cmdEcho || !promptEcho)
//                        {
//                            prompt = as[k];
//                            s = truncateResponse(s);
//                        }
//                        return s;
//                    }
//                    k++;
//                }
//            }
//        } while (true);
//        return null;
//    }

    public String getTerminalType()
    {
        if(this.terminalType == null)
        {
            return super.getTerminalType();
        }
        else
        {
            return terminalType;
        }
    }

    public void setTerminalType(String terminalType)
    {
        this.terminalType = terminalType;
    }

    public String getCmdSuffix()
    {
        return cmdSuffix;
    }

    /**
     * 设置命令后缀，通常为回车+换行<br>
     * @param cmdSuffix String
     */
    public void setCmdSuffix(String cmdSuffix)
    {
        this.cmdSuffix = cmdSuffix;
    }

//    private boolean isLogin;
    private String loginPrompt;
    private String passwdPrompt;
    private String prompt;
    private String loginMessage;
    String promptStrings[];
    String partialData;
    boolean cmdEcho;
    boolean promptEcho;
    int readBufferLength;
    String lastCommand;
    String cmdSuffix;
    static boolean debugFlag = false;
    private String terminalType;

//****** 代码段: 调试代码 *******************************************************************************/
    static public void main(String[] args)
    {

        System.out.println("=============================================================");
        TelnetSession telnet = new TelnetSession();
        telnet.setTerminalType("ANSI");
        try
        {
            telnet.setPasswdPrompt(">");
            telnet.connect("10.61.94.25", 1501);
            telnet.setSocketTimeout(20000);
            telnet.setLoginPrompt("login  :");
            telnet.setPasswdPrompt("Password:");
            telnet.login("root", "root");
            telnet.setPrompt("DBMS>");
            telnet.send("dbms");
//            System.out.print(telnet.send("1002"));

            String strResult = telnet.send("add_multi_ip:type=1:ipaddr=112.12.11.10:submask=255.255.255.0:ipnum=2");
            System.out.println(strResult);

            if(strResult.indexOf("Command Excute Success") > 0)
            {
                System.out.println("Ok");
            }
            else
            {
                System.out.println("Failed");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("服务器返回信息：");
            System.out.println(telnet.getPartialData());
        }

        try
        {
            telnet.disconnect();
        }
        catch(Exception ex)
        {
        }
    }
}
