package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.basicClass.MessageBuble;
import com.example.myapplication.basicClass.MessageEnvironment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for MessageEnvironment
 * @author Scott Ferrageau de St Amand (u7303997)
 */

public class MessageEnvironmentTest {

    MessageEnvironment messageEnvironment;
    List<String> userIds;
    List<MessageBuble> messageList;

    @Before
    public void setUp() {
        String userId1 = "00xsB9R1pNZpFwegYZRfnyAUfM90";
        String userId2 = "01TqL5aRxLWIfZXTC8eR18DW6102";

        userIds = new ArrayList<>();
        userIds.add(userId1);
        userIds.add(userId2);

        MessageBuble message1 = new MessageBuble("test message 1", userId1,
                 "2024-05-15 15:17:36");
        MessageBuble message2 = new MessageBuble("test message 2", userId2,
                "2024-05-15 18:04:44");


        messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        messageEnvironment = new MessageEnvironment("testId1", userIds,
                "2024-05-15 15:17:36","01TqL5aRxLWIfZXTC8eR18DW6102", "2024-05-15 18:04:44",
                messageList);
    }

    @Test
    public void testGetters() {
        assertEquals("testId1", messageEnvironment.getEnvironmentId());
        assertEquals(userIds, messageEnvironment.getUserIds());
        assertEquals("2024-05-15 15:17:36", messageEnvironment.getTimestamp());
        assertEquals("01TqL5aRxLWIfZXTC8eR18DW6102", messageEnvironment.getRecentSenderId());
        assertEquals("2024-05-15 18:04:44", messageEnvironment.getRecentMessageTimestamp());
        assertEquals(messageList, messageEnvironment.getMessageList());
    }

    @Test
    public void testSetters() {
        messageEnvironment.setEnvironmentId("testId2");
        assertEquals("testId2", messageEnvironment.getEnvironmentId());

        String userId3 = "1IMjTfrBvlVh4kTonxnHwiLuqGJ3";
        String userId4 = "1Iuaf4SCIFgrnjNVCPzEDHG74wg2";
        List<String> newUserIds = new ArrayList<>();
        newUserIds.add(userId3);
        newUserIds.add(userId3);

        messageEnvironment.setUserIds(newUserIds);
        assertEquals(newUserIds, messageEnvironment.getUserIds());

        messageEnvironment.setTimestamp("2024-05-15 15:22:31");
        assertEquals("2024-05-15 15:22:31", messageEnvironment.getTimestamp());

        messageEnvironment.setRecentMessageTimestamp("2024-05-15 15:22:31");
        assertEquals("2024-05-15 15:22:31", messageEnvironment.getRecentMessageTimestamp());

        messageEnvironment.setRecentSenderId(userId4);
        assertEquals(userId4, messageEnvironment.getRecentSenderId());

    }
    @Test
    public void testAddMessage() {
        MessageBuble newMessage = new MessageBuble("test message 3", "00xsB9R1pNZpFwegYZRfnyAUfM90","2024-05-15 20:22:31");
        messageEnvironment.addMessage(newMessage);
        assertTrue(messageEnvironment.getMessageList().contains(newMessage));
    }


}
