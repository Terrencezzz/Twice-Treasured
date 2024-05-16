package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.view.View;

import com.example.myapplication.basicClass.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.example.myapplication.Adapters.ChatMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for the ChatMenuAdaptor
 * @author Scott Ferrageau de St Amand (u7303997)
 * Influenced by Wen's CategoryAdapterTest
 */



public class ChatMenuAdapterTest {

    ChatMenuAdapter chatMenuAdapter;

    @Before
    public void setUp() {
        Context context = Mockito.mock(Context.class);
        User user1 = new User("00xsB9R1pNZpFwegYZRfnyAUfM90", "Luke Hall", "eellis@gmail.com",
                "iRo7uRfv", "https://piccn.ihuaben.com/pic/community/201812/0-1545750983564-W3gQ_400-400.jpeg?x-oss-process=image/resize,w_640",
                "canberra");

        User user2 = new User("01TqL5aRxLWIfZXTC8eR18DW6102", "Dominique Clark", "jmorris@hotmail.com",
                "2rJEgqW2", "https://img2.woyaogexing.com/2022/04/05/fa5f73712cc5413eb8ac9d9bfec2d3e6%21400x400.jpeg",
                "tasmania");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        chatMenuAdapter = new ChatMenuAdapter(context, userList);
    }

    @Test
    public void testGetItemCount(){
        assertEquals(2, chatMenuAdapter.getItemCount());
    }

    @Test
    public void testCheckViewHolder() {
        View view = Mockito.mock(View.class);
        ChatMenuAdapter.UserViewHolder viewHolder = new ChatMenuAdapter.UserViewHolder(view);
        assertNotNull(viewHolder);
    }

}
