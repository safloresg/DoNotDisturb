package donotdisturb.fime.mx.donotdisturb;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void IsInTimeRange_CurHourGreaterThanStartHourAndSmallerThanEndHour_ReturnsTrue() {
//        public static boolean isInTimeRange(int startHour,int startMin, int endHour, int endMin,int curHour,int curMin)

        boolean result = CallReceiver.isInTimeRange(3, 0, 5, 0, 4, 0);
        assertTrue(result);
    }

    @Test
    public void IsInTimeRange_CurHourSmallerThanStartHourAndSmallerThanEndHour_ReturnsFalse()
    {
        boolean result = CallReceiver.isInTimeRange(3, 0, 5, 0, 1, 0);
        assertFalse(result);
    }

    
}