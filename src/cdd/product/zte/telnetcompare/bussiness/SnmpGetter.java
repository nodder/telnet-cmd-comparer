package cdd.product.zte.telnetcompare.bussiness;

import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;

public class SnmpGetter
{
    private static final int VERSION = SnmpTarget.VERSION2C;
    private static final String COMMUNITY = "public";
    private static final int SNMPPORT = 161;
    private static final int RETRY = 1;
    private static final int TIMEOUT = 2;
    
    public static String getSysName(String ipAddr) throws Exception
    {
        return getBySnmp(".1.3.6.1.2.1.1.5.0", ipAddr);
    }
    
    public static String getSysObjectID(String ipAddr) throws Exception
    {
        return getBySnmp(".1.3.6.1.2.1.1.2.0", ipAddr);
    }
    
    private static String getBySnmp(String oid, String ipAddr) throws Exception
    {
        SnmpTarget target = getSnmpTarget(ipAddr);

        target.setObjectIDList(null);
        target.addObjectID(oid);

        String returnValue = target.snmpGet();
        if(returnValue == null)
        {
            throw new Exception(target.getErrorString());
        }
        
        return returnValue;
    }

    private static SnmpTarget getSnmpTarget(String ipAddr)
    {
        SnmpTarget target = new SnmpTarget();

        target.setTargetHost(ipAddr);
        target.setTargetPort(SNMPPORT);
        target.setRetries(RETRY);
        target.setTimeout(TIMEOUT);
        target.setSnmpVersion(VERSION);
        target.setCommunity(COMMUNITY);
        
        return target;
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println(getSysName("10.63.174.1"));
        System.exit(0);
    }
}
