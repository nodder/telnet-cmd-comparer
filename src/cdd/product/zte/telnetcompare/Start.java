package cdd.product.zte.telnetcompare;

import cdd.product.zte.telnetcompare.ui.TelnetCompareFrm;

public class Start
{
    public static String beyondCompare_dir;
    
    public static void main(String[] args)
    {
        beyondCompare_dir = args[0];
        new TelnetCompareFrm();
    }
}
