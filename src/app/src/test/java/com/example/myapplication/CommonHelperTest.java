package com.example.myapplication;

import static com.example.myapplication.common.CommonHelper.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.regex.Pattern;

public class CommonHelperTest {

    @Test
    public void testGetCurrentTimestamp() {
        String timestamp = getCurrentTimestamp();
        String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        assertTrue( "Timestamp format is invalid: " + timestamp,pattern.matcher(timestamp).matches());
        assertNotNull(timestamp);
    }
}
