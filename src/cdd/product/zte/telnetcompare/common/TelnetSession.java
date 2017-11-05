package cdd.product.zte.telnetcompare.common;

import java.io.IOException;
import java.net.SocketException;

import de.mud.telnet.TelnetWrapper;

/**
 * <p>�ļ�����: TelnetSession.java</p>
 * <p>�ļ�����: ��װtelnetЭ��</p>
 * <p>��Ȩ����: ��Ȩ����(C)2001-2004</p>
 * <p>��    ˾: ����ͨѶ�ɷ����޹�˾</p>
 * <p>����ժҪ: </p>
 * <p>����˵��: Ҫ����JTA��������������disconnet()</p>
 * <p>������ڣ�2004��5��26��</p>
 * <p>�޸ļ�¼1:</p>
 * <pre>
 *    �޸����ڣ�
 *    �� �� �ţ�
 *    �� �� �ˣ�
 *    �޸����ݣ�
 * </pre>
 * <p>�޸ļ�¼2��</p>
 * @version 1.0
 * @author  jxj
 *<b> <p>Ӧ�þ���: </p></b>
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
        &nbsp;&nbsp;&nbsp;    telnet.disconnect();// ��֤�Ͽ�����<br>
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
     * ���ܣ�����telnet���������˿ں�ΪĬ��23
     * @param remoteHost String telnet��������ַ
     * @throws IOException
     */
    public void connect(String remoteHost) throws IOException
    {
        super.connect(remoteHost, 23);
    }

    /**
     * ���ܣ�����telnet������
     * @param remoteHost String telnet��������ַ
     * @param remotePort int �˿ں�
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
     * ��õ�¼��Ϣ����ʵ�ʲ��Է���������
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
     * Ĭ��Ϊtrue
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
     * ���ܣ���¼
     * @param userName String ����û���Ϊ��,�������û���
     * @param password String �������Ϊ��,����������
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
     * ���ܣ���������,������ִ�н��
     * @param command String ����
     * @throws IOException
     * @return String ����ִ�н�� ���û��������ʾ��,�򷵻�Ϊ��
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
     * �����Ƿ����
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
     * ����socket��ʱ����λms
     * @param i int ��ʱ
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
     * �ӷ�����������Ϣ�еȴ��ַ���<br>
     * Waits for a string to come from the remote host and returns all those
     * characters which are received until that happens (including the string
     * being waited for). Times out in case the expected string were not
     * received. The partial data read can be obtained using getPartialData().
     * This will return the data that was read until the time-out.
     * @param strWait String ���ȴ����ַ���
     * @throws IOException
     * @return String ��Ӧ��Ϣ
     * ����������Է���������,����ע�͵�,ֱ�����ø����е�ʵ��
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
                    {//��������û���ַ���
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
                        {// ����������ͻس����������֣���֪��ʲô�����ִ�е�����
                            this.debugPrint("���ͻس�");
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
     * ���������׺��ͨ��Ϊ�س�+����<br>
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

//****** �����: ���Դ��� *******************************************************************************/
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
            System.out.println("������������Ϣ��");
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
