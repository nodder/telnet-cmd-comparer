package cdd.product.zte.telnetcompare.bussiness;

import java.io.File;
import java.io.IOException;

import cdd.product.zte.telnetcompare.Start;

public class TextComparer
{
    String beyondCompareFile = new File(Start.beyondCompare_dir, "BComp.com").getAbsolutePath();
    
    public void compareText(File output1, File output2)
    {
        runProcess(beyondCompareFile + " " + output1.getAbsolutePath() + " " + output2.getAbsolutePath());
    }

    public static void runProcess(String cmd)
    {
        ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));      
        try
        {
            Process p = pb.start();
            System.out.println("Please wait...");
            p.waitFor();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
