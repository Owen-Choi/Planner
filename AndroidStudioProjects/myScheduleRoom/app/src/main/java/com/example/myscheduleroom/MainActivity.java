package com.example.myscheduleroom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomManager mng = new RoomManager();

        GameUser gameUser1 = new GameUser(1, "orange");
        GameUser gameUser2 = new GameUser(2, "apple");
        GameUser gameUser3 = new GameUser(3, "banana");

        GameRoom gameRoom = RoomManager.createRoom(gameUser1);
        System.out.println("방 만들어짐 : " + mng.getRoom(gameRoom));

        gameRoom.enterUser(gameUser2);
        gameRoom.enterUser(gameUser3);
        System.out.println("다 들어오고 리스트 : " + gameRoom.getUserList());
        gameRoom.exitUser(gameUser2);
        gameRoom.exitUser(gameUser1);
        System.out.println("2명 나감 : " + gameRoom.getUserList());
        gameRoom.exitUser(gameUser3);

        int roomidx = RoomManager.getRoom(gameRoom); //null?

        if(roomidx != 0){
            System.out.println("방이 아직 있네");
        }
        else{
            System.out.println("방이 없어졌네");
        }
    }

}