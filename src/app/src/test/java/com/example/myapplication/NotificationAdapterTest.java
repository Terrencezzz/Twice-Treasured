package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.Adapters.NotificationAdapter;
import com.example.myapplication.basicClass.Notification;

import java.util.ArrayList;
public class NotificationAdapterTest {

    private ArrayList<Notification> notifications = new ArrayList<>();
    private NotificationAdapter notificationAdapter = null;

    @Before
    public void setUp() {


        Notification notification_1 = new Notification();
        notification_1.setNotiID("1");
        Notification notification_2 = new Notification();
        notification_1.setNotiID("2");

        notifications.add(notification_1);
        notifications.add(notification_2);

        notificationAdapter = new NotificationAdapter(notifications);

    }

    ViewGroup viewgroup;
    View view;

    @Test
    public void checkItemCount() {
        assertEquals(2,notifications.size());
    }

    @Test
    public void checkViewHolder(){

        view = Mockito.mock(View.class);
        NotificationAdapter.viewholder viewHolder = new NotificationAdapter.viewholder(view);
        assertNotNull(viewHolder);
    }

}
