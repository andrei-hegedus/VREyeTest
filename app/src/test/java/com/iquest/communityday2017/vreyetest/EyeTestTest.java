package com.iquest.communityday2017.vreyetest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author andrei.hegedus
 */

public class EyeTestTest {

    @Test
    public void testVerificationRow0(){
        EyeTest et = new EyeTest();
        assertEquals(100, et.verify("F P", 0));
        assertEquals(100, et.verify(" FP", 0));
        assertEquals(50, et.verify("F Z", 0));
    }

    @Test
    public void testVerificationRow1(){
        EyeTest et = new EyeTest();
        assertEquals(100, et.verify("TOZ", 1));
        assertEquals(100, et.verify("T O Z", 1));
        assertEquals(66, et.verify("T O 2", 1));
        assertEquals(0, et.verify("F Z", 1));
        assertEquals(0, et.verify("F Z J K L", 1));
    }
}
