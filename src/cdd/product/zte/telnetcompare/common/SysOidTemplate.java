package cdd.product.zte.telnetcompare.common;

public class SysOidTemplate
{
    private static final String[] SYSOID_MAP = new String[]
    {
        ".1.3.6.1.4.1.3902.1004.9806.2.1.1", //ZXDSL9806H
        ".1.3.6.1.4.1.3902.1004.9806.3.1.1", //ZXDSL9806I
        ".1.3.6.1.4.1.3902.1015.9812.1.1.1", //ZXDSL9812
        ".1.3.6.1.4.1.3902.1015.9816.1.1.1", //ZXDSL9816
        ".1.3.6.1.4.1.3902.1015.9836.1.1.1", //ZXDSL9836
        ".1.3.6.1.4.1.3902.1015.9856.1.1.1", //ZXDSL9856
        ".1.3.6.1.4.1.3902.1015.822.1.1.1", //F822
        ".1.3.6.1.4.1.3902.1015.821.1.1.1", //F821
        ".1.3.6.1.4.1.3902.1015.802.1.1.1", //F802
        ".1.3.6.1.4.1.3902.1015.803.2.1.1", //F803-16
        ".1.3.6.1.4.1.3902.1015.803.2.2.1", //F803-8
        ".1.3.6.1.4.1.3902.1015.803.2.3.1", //F803G-16
        ".1.3.6.1.4.1.3902.1015.803.2.4.1", //F803G-8
        ".1.3.6.1.4.1.3902.1015.803.1.1.1", //F803V1
        ".1.3.6.1.4.1.3902.1015.402.1.1.1", //F402
        ".1.3.6.1.4.1.3902.1015.809.1.1.1", //F809
        ".1.3.6.1.4.1.3902.1015.823.1.1.1", //F823
        ".1.3.6.1.4.1.3902.1015.829.1.1.1", //F829
        ".1.3.6.1.4.1.3902.1015.822.2.1.1", //F822P
        ".1.3.6.1.4.1.3902.1015.803.3.1.1"  //F803V3
    };
    
    private static final String[] SYSOID_9800 = new String[] 
    {
        ".1.3.6.1.4.1.3902.1011.9800" //ZXDSL9800
    };
                                                           
    
    private SysOidTemplate()
    {
    }
    
    public static boolean isMapOr9800(String sysOid)
    {
        return arrayContainsKey(SYSOID_MAP, sysOid) 
           ||  arrayContainsKey(SYSOID_9800, sysOid);
    }
    
    private static boolean arrayContainsKey(String[] arr, String key)
    {
        for(String item : arr)
        {
            if(item.equals(key))
            {
                return true;
            }
        }
        
        return false;
    }
}
