package com.example.chattproject.dao;

import com.example.chattproject.dto.ChatRoom;
import com.example.chattproject.service.flieService.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;


// 추후 DB 와 연결 시 Service 와 Repository(DAO) 로 분리 예정
@Repository
@Slf4j
public class ChatRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {chatRoomMap = new LinkedHashMap<>();

    }

    // 채팅방 삭제에 따른 채팅방의 사진 삭제를 위한 flieService 선언
//    @Autowired
//    FileService fileService;

    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom(){
        // 채팅방 생성 순서를 최근순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms); // 역순으로

        return chatRooms;
    }

    // roomId 기준으로 채팅방 찾기
    public ChatRoom findRoomById(String roomId) {

        return chatRoomMap.get(roomId);
    }

    // roomName 로 채팅방 만들기
    public ChatRoom creatChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt){

        //roomName 과 roomPwd 로 chatRoom 빌드 후 return

        ChatRoom chatRoom =  ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .roomPwd(roomPwd)   //채팅방 패스워드
                .secretChk(secretChk)  //채팅방 잠금 여부
                .userList(new HashMap<String, String>())
                .userCount(0)   // 채팅방 참여 인원 수
                .maxUserCnt(maxUserCnt) // 최대 인원 수 제한
                .build();

        // map에 채팅룸 아이디와 만들어진 채팅룸을 저장
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    // 채팅방 인원 +1
    public void plusUserCnt(String roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() +1);
    }

    // 채팅방 인원 -1
    public void minusUserCnt(String roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() -1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomID){
        ChatRoom room = chatRoomMap.get(roomID);

        log.info("참여인원 확인 [{}, {}]", room.getUserCount(), room.getMaxUserCnt());

        if(room.getUserCount() + 1 > room.getMaxUserCnt()){
            return false;
        }

        return true;

    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String userName){
        ChatRoom room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후  userList 에 추가
        room.getUserList().put(userUUID, userName);

        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username){
        ChatRoom room = chatRoomMap.get(roomId);
        String tmp = username;

        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
        while(room.getUserList().containsValue(tmp)){
            int ranNum = (int)(Math.random()*100)+1;

            tmp = username + ranNum;
        }

        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userUUID){
        ChatRoom room = chatRoomMap.get(roomId);
        room.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoom room = chatRoomMap.get(roomId);

        return room.getUserList().get(userUUID);
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoom room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 re셔구
        room.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }

    // 채팅방 비밀번호 조회
    public boolean confirmPwd(String roomId, String roomPwd){

        return roomPwd.equals(chatRoomMap.get(roomId).getRoomPwd());
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId){
        try{
            // 채팅방 삭제
            chatRoomMap.remove(roomId);

            // 채팅방 안에 있는 파일 삭제
//            fileService.deleteFileDir(roomId);

            log.info("삭제 완료 roomId : {}", roomId);
        }catch (Exception e){   // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());

        }
    }

}
