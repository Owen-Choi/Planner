package com.example.myscheduleroom;

import java.net.Socket;
public class GameUser {

    private int id; 			// Unique ID
    private GameRoom room; 		// 각자의 유저가 어느 룸에 속해있는지
    private Socket sock;
    private String nickName;



    public GameUser() { // 아무런 정보가 없는 깡통 유저
    }


    public GameUser(String nickName) { // 닉네임 정보만 가지고 생성
        this.nickName = nickName;
    }


    public GameUser(int id, String nickName) { // UID, 닉네임 정보를 가지고 생성
        this.id = id;
        this.nickName = nickName;
    }


    public void enterRoom(GameRoom room) {
        room.enterUser(this); // 룸에 유저 추가 - 입장시킴
        this.room = room; // 이 유저는 이 룸에 속해있다고 표시
    }


    public void exitRoom(GameRoom room){
        this.room = null; //이 유저는 어느 룸에도 안속함
        // 퇴장처리(화면에 메세지를 준다는 등)
        // ...exitUser 안해줘도 되나?
    }


    //id 겟셋
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //룸 겟셋
    public GameRoom getRoom() {
        return room;
    }
    public void setRoom(GameRoom room) {
        this.room = room;
    }

    //각 유저들이 사용할 소켓 겟셋
    public Socket getSock() {
        return sock;
    }
    public void setSock(Socket sock) {
        this.sock = sock;
    }

    //닉네임 겟셋
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    /*
        equals와 hashCode를 override 해줘야, 동일유저를 비교할 수 있다
        비교할 때 -> gameUser 간 equals 비교, list에서 find 등
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameUser gameUser = (GameUser) o;

        return id == gameUser.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


}

