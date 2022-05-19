package com.example.myscheduleroom;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomManager {

    private static List roomList; // 방의 리스트
    private static AtomicInteger atomicInteger; //멀티스레드에서 다른 스레드가 메모리값을 덮어쓰는걸 방지

    static {
        roomList = new ArrayList();
        atomicInteger = new AtomicInteger();
    }

    //생성자
    public RoomManager() {

    }

    public static GameRoom createRoom() { // 룸을 새로 생성(빈 방)
        int roomId = atomicInteger.incrementAndGet(); // room id 새로운 번호 받음
        GameRoom room = new GameRoom(roomId);

        roomList.add(room); //방리스트에 추가
        System.out.println("Room Created");
        return room; //방 객체 리턴
    }


    public static GameRoom createRoom(GameUser owner) { // 유저가 방을 생성할 때 사용(유저가 방장으로 들어감)
        int roomId = atomicInteger.incrementAndGet();// room id 채번
        GameRoom room = new GameRoom(roomId);

        room.enterUser(owner); //사람도 들어왔으니 사람 추가
        room.setOwner(owner); //그 사람 방장

        roomList.add(room);
        System.out.println("Room Created");
        return room;
    }


    public static GameRoom createRoom(List users) { //여러 유저가 한꺼번에 들어가면서 방 생성
        int roomId = atomicInteger.incrementAndGet();// room id 채번

        GameRoom room = new GameRoom(roomId);
        room.enterUser(users);

        roomList.add(room);
        System.out.println("Room Created");
        return room;
    }

//    public static GameRoom getRoom(GameRoom gameRoom){
//
//        int index = roomList.indexOf(gameRoom);
//
//        if(index > 0){
//            return roomList.get(index);
//        }
//        else{
//            return null;
//        }
//    }
    //방 정보인 인덱스 리턴
    public static int getRoom(GameRoom gameRoom){ //ob대신 Gameroom

        return roomList.indexOf(gameRoom);

    }



    public static void removeRoom(GameRoom room) {
        room.close();
        roomList.remove(room); // 전달받은 룸을 룸리스트에서 제거
        System.out.println("Room Deleted!");
    }


    public static int roomCount() {
        return roomList.size(); //룸리스트의 길이
    }
}

//fix 되면 방리스트에서 검색되지 않도록 기능 추가하기
