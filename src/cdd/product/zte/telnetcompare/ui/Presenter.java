package cdd.product.zte.telnetcompare.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import cdd.product.zte.telnetcompare.bussiness.RunnConfgTelnetSession;
import cdd.product.zte.telnetcompare.bussiness.SnmpGetter;
import cdd.product.zte.telnetcompare.bussiness.TextComparer;
import cdd.product.zte.telnetcompare.common.SysOidTemplate;
import cdd.product.zte.telnetcompare.common.TelnetInfo;


public class Presenter
{
    private RunnConfgTelnetSession runConfig = new RunnConfgTelnetSession();
    private TextComparer compare = new TextComparer();

    public void snapshot(File outputFile, TelnetInfo telnetInfo) throws IOException
    {
        String result = this.runConfig.runCmd(telnetInfo);
        saveResult(outputFile, result);
    }
    
    public void compareSnapshot(File output1, File output2)
    {
        compare.compareText(output1, output2);
    }

    private void saveResult(File outputFile, String result) throws FileNotFoundException
    {
        PrintWriter pw = null;
        try
        {
            pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(outputFile)));
            pw.println(result);
        }
        finally
        {
            if(pw != null)
            {
                pw.close();
            }
        }
    }

    public void saveParams(TelnetInfo telnetInfo) throws IOException
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream("params.obj"));
            out.writeObject(telnetInfo);
        }
        finally
        {
            if(out != null)
            {
                out.close();
            }
        }
    }
    
    public TelnetInfo getParams() throws Exception
    {
        File paramObjFile = new File("params.obj");
        if(!paramObjFile.exists())
        {
            return null;
        }
        
        ObjectInputStream oin = null;
        try
        {
            oin = new ObjectInputStream(new FileInputStream(paramObjFile));
            return (TelnetInfo)oin.readObject();
        }
        finally
        {
            if(oin != null)
            {
                oin.close();
            }
        }
    }

    public String refreshPromptSnmp(String ipAddr) throws Exception
    {
        String sysName = SnmpGetter.getSysName(ipAddr);
        String sysOid = SnmpGetter.getSysObjectID(ipAddr);
        
        if(SysOidTemplate.isMapOr9800(sysOid))
        {
            return sysName + ">";
        }
        
        return sysName + "#";
    }
}
