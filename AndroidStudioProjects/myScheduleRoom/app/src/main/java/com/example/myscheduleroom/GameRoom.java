package com.example.myscheduleroom;


import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    private int id; // 룸 ID
    private List<GameUser> userList; //유저 리스트
    private GameUser roomOwner; // 방장
    private String roomName; // 방 이름

    //생성자
    public GameRoom(int roomId) { // 룸 id로 방 생성, 빈 방임
        this.id = roomId;
        userList = new ArrayList();
    }

    public GameRoom(GameUser user) { // 유저가 방을 만들때
        userList = new ArrayList();
        user.enterRoom(this); //이 사람 방에 입장시킴
        userList.add(user); // 유저리스트에 유저를 추가시킨 후
        this.roomOwner = user; // 방장을 유저로 만듦
    }

    public GameRoom(List<GameUser> users) {  //리스트 형태로 유저가 한꺼번에 들어가면서 방 생성 시
        this.userList = users; // 유저리스트 복사

        for(GameUser user : users){ //모든 유저 방으로 입장시킴
            user.enterRoom(this);
        }
        this.roomOwner = userList.get(0); // 첫번째 유저를 방장으로 설정
    }

    public void enterUser(GameUser user) { //유저를 이 룸에 입장시킴
        user.enterRoom(this); //this는 이 room 객체 - enterRoom(GameRoom room)
        userList.add(user); //유저리스트에 이사람 추가
    }

    public void enterUser(List<GameUser> users) { //단체 유저 추가
        for(GameUser gameUser : users){
            gameUser.enterRoom(this);
        }
        userList.addAll(users);
    }


    public void exitUser(GameUser user) { //유저 내보냄
        user.exitRoom(this);
        userList.remove(user); // 해당 유저를 방에서 내보냄

        if (userList.size() < 1) { // 모든 인원이 다 방을 나갔다면
            RoomManager.removeRoom(this); // 이 방을 제거한다.
            return;
        }

        if (userList.size() < 2) { // 방에 남은 인원이 1명 이하라면
            this.roomOwner = userList.get(0); // 리스트의 첫번째 유저가 방장이 된다.
            return;
        }
    }


    public void close() { //방 삭제
        for (GameUser user : userList) { //해당 룸의 유저를 다 퇴장시킴
            user.exitRoom(this);
        }
        this.userList.clear(); //이 방 유저리스트 다 삭제
        this.userList = null;
    }


    public GameUser getUserByNickName(String nickName) { // 닉네임을 통해서 방에 속한 유저를 리턴함

        for (GameUser user : userList) { //유저리스트에서 받아온 닉네임과 일치하는게 있나
            if (user.getNickName().equals(nickName)) {
                return user;  //있으면 유저 객체 리턴
            }
        }
        return null; // 찾는 유저가 없다면
    }

    public GameUser getUser(GameUser gameUser) { // GameUser 객체로 get

        int index = userList.indexOf(gameUser);  //index는 유저리스트에서 그 게임유저의 인덱스

        // 유저가 존재한다면 유저의 객체 리턴           (gameUser의 equals로 비교)???
        if(index > 0){
            return userList.get(index);            //이거 이용-----------------
        }
        // 유저가 없다면
        else{
            return null;
        }
    }


    public void setOwner(GameUser gameUser)  // 특정 사용자를 방장으로 설정
    {
        this.roomOwner = gameUser;
    }

    public void setRoomName(String name) // 방 이름을 설정
    {
        this.roomName = name;
    }

    public String getRoomName() { // 방 이름을 가져옴
        return roomName;
    }

    public int getUserSize() { // 유저의 수를 리턴
        return userList.size();
    }

    public GameUser getOwner() { // 방장을 리턴
        return roomOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public GameUser getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(GameUser roomOwner) {
        this.roomOwner = roomOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRoom gameRoom = (GameRoom) o;

        return id == gameRoom.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


    public void broadcast(byte[] data) { //해당 byte 배열의 데이터를 방 모든 인원에게 broadcast
        for (GameUser user : userList) { // 방에 속한 유저의 수만큼 반복
            // 각 유저에게 데이터를 전송하는 메서드?
            // user.SendData(data);

//			try {
//				user.sock.getOutputStream().write(data); // 이런식으로 바이트배열을 보낸다.
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
    }
}

//Firebase Dynamic Link를 활용하여 사용자 초대 해서 enterRoom 하게 하기