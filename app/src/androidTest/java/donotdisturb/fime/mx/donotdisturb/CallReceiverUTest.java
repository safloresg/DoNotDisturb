package donotdisturb.fime.mx.donotdisturb;

import junit.framework.Assert;

import org.junit.Test;
import static org.junit.Assert.assertTrue;



/**
 * Created by sergio on 5/1/16.
 */
public class CallReceiverUTest
{
    @Test
    public void IsInTimeRange_CurHourGreaterThanStartHourAndSmallerThanEndHour_ReturnsTrue()
    {
//        public static boolean isInTimeRange(int startHour,int startMin, int endHour, int endMin,int curHour,int curMin)

        boolean result = CallReceiver.isInTimeRange(2,0,3,0,4,0);
        assertTrue(result);
    }
}
