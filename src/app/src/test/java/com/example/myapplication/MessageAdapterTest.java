package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.view.View;

import com.example.myapplication.Adapters.MessageAdapter;
import com.example.myapplication.basicClass.MessageBuble;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for the MessageAdapter.
 * @author Scott Ferrageau de St Amand (u7303997)
 * Influenced by Wen's CategoryAdapterTest
 */

public class MessageAdapterTest {

    MessageAdapter messageAdapter;

    @Before
    public void setUp() {
        Context context = Mockito.mock(Context.class);

        MessageBuble messageBuble1 = new MessageBuble("test message 1", "00xsB9R1pNZpFwegYZRfnyAUfM90",
                "2024-05-15 15:17:36");

        MessageBuble messageBuble2 = new MessageBuble("test message 2", "01TqL5aRxLWIfZXTC8eR18DW6102",
                "2024-05-15 18:04:44");


        List<MessageBuble> messageList = new ArrayList<>();
        messageList.add(messageBuble1);
        messageList.add(messageBuble2);

        messageAdapter = new MessageAdapter(context, "00xsB9R1pNZpFwegYZRfnyAUfM90", messageList );
    }

    @Test
    public void testGetItemCount(){
        assertEquals(2, messageAdapter.getItemCount());
    }


    //As there are two possible view holders for this adapter, the test for sender and receiver
    //are separate.
    @Test
    public void testCheckViewHolderSender() {
        View view = Mockito.mock(View.class);
        MessageAdapter.MessageViewHolderSender viewHolder = new MessageAdapter.MessageViewHolderSender(view);
        assertNotNull(viewHolder);
    }

    @Test
    public void testCheckViewHolderReceiver() {
        View view = Mockito.mock(View.class);
        MessageAdapter.MessageViewHolderReciever viewHolder = new MessageAdapter.MessageViewHolderReciever(view);
        assertNotNull(viewHolder);
    }

}