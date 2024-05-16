package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.basicClass.MessageBuble;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for MessageBubble
 * @author Scott Ferrageau de St Amand (u7303997)
 */
public class MessageBubbleTest {

    String message, senderId, timeStamp;

    MessageBuble messageBuble;

    @Before
    public void setUp() {
        message = "test message";
        senderId = "test senderId";
        timeStamp = "test timeStamp";

        messageBuble = new MessageBuble(message, senderId, timeStamp);
    }

    @Test
    public void testGetters() {
        assertEquals("test message", messageBuble.getMessage());
        assertEquals("test senderId", messageBuble.getSenderId());
        assertEquals("test timeStamp", messageBuble.getTimestamp());
    }

    @Test
    public void testSetters() {
        messageBuble.setMessage("new test message");
        messageBuble.setSenderId("new test senderId");
        messageBuble.setTimestamp("new test timeStamp");

        assertEquals("new test message", messageBuble.getMessage());
        assertEquals("new test senderId", messageBuble.getSenderId());
        assertEquals("new test timeStamp", messageBuble.getTimestamp());

    }

}
