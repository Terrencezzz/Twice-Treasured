package com.example.myapplication.basicClass;

public class NoticeFactory {

    /**
     * Create different Notice by factory
     * @author Wen Li @u7706423
     * */
    public Notice createNotice(String type){
        if(type.equalsIgnoreCase("Favorite")){
            return new FavoriteNotice();
        }else if(type.equalsIgnoreCase("User")){
            return new UserNotice();
        }

        return null;
    }
}
