package kakaoToy1.kakaoToy1.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private static List<String> onlineList = new ArrayList<>();
    private static List<WebSocketSession> sessionList = new ArrayList<>();
    Map<String, WebSocketSession> userSession = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        Map<String, Object> dataMap = new HashMap<>();

        //master status
        String masterStatus = getMasterStatus();
        String time = changeDateTimePattern("hh:mm a, E");

        // message data
        String senderId = (String) session.getAttributes().get("sessionId");
        String payload = message.getPayload();

        dataMap = jsonToMap(payload);
        dataMap.put("senderId", senderId);
        dataMap.put("time", time);
        dataMap.put("masterStatus", masterStatus);
        dataMap.put("onlineList", onlineList);
        
        String receiverId = (String) dataMap.get("receiverId");
        
        log.info("final dataMap >>> " + dataMap);
        
        //send a message
        System.out.println("userSession.get(receiverId) = " + userSession.get(receiverId));
        String msg = objectMapper.writeValueAsString(dataMap);

        if (userSession.get(receiverId) != null) {
            userSession.get(receiverId).sendMessage(new TextMessage(msg));
        }

        if(!senderId.equals(receiverId)){
            dataMap.put("receiverId", senderId);
            msg = objectMapper.writeValueAsString(dataMap);
            session.sendMessage(new TextMessage((msg)));
        }
    }

    // connection established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        //save into session List
        String senderId = (String) session.getAttributes().get("sessionId");
        sessionList.add(session);
        onlineList.add(senderId);
        userSession.put(senderId, session);

        // as master send message to all
        if (senderId.equals("master")){
            TextMessage msg = new TextMessage(senderId + " 님이 접속했습니다.");
            sendToAll(msg, senderId);
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("message", senderId + "님이 접속하셨습니다.");
            data.put("receiverId", "master");
            data.put("newOne", senderId);

            TextMessage msgToMaster = new TextMessage(objectMapper.writeValueAsString(data));
            handleMessage(session, msgToMaster);
        }
        log.info(session + " client connected");
    }

    //connection closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String senderId = (String) session.getAttributes().get("sessionId");
        sessionList.remove(session);
        onlineList.remove(senderId);
        userSession.remove(senderId);

        //as master send to all
        if (senderId.equals("master")) {
            TextMessage msg = new TextMessage(senderId + " 님이 퇴장했습니다.");
            sendToAll(msg, senderId);
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("message", senderId + "님이 퇴장하셨습니다.");
            data.put("receiverId", "master");
            data.put("outOne", senderId);

            TextMessage msg = new TextMessage(objectMapper.writeValueAsString(data));
            handleMessage(session, msg);
        }
        log.info(session + "client disconnected");
    }

    public void getOnlineList() throws IOException {
    }

    //send a message to all
    private void sendToAll(TextMessage message, String senderId) throws IOException {
        //json test
        Map<String, Object> dataMap = new HashMap<>();

        String masterStatus = getMasterStatus();

        //sending time
        String time = changeDateTimePattern("hh:nn a E");

        //message data
        String payload = message.getPayload();

        log.info("payload = " + payload);

        dataMap.put("message", message.getPayload());
        dataMap.put("senderId", senderId);
        dataMap.put("time", time);
        dataMap.put("masterStatus", masterStatus);
        dataMap.put("onlineList", onlineList);
        dataMap.put("newOne", "master");

        String receiverId = (String) dataMap.get("receiverId");

        log.info("final dataMap = " + dataMap);

        //send a message
        log.info("receiver session = "+ userSession.get(receiverId));

        for (String us : userSession.keySet()) {
            dataMap.put("receiverId", us);
            String msg = objectMapper.writeValueAsString(dataMap);
            userSession.get(us).sendMessage(new TextMessage(msg));
        }
    }

    private Map<String, Object> jsonToMap(String jsonString) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });

        return map;
    }

    public void senOnlineList(WebSocketSession session) throws IOException{
        Map<String, Object> map = new HashMap<>();

        map.put("onlineList", onlineList);
        String list = objectMapper.writeValueAsString(map);

        log.info("map = " + map);
        session.sendMessage(new TextMessage(list));
    }


    private String getMasterStatus() {
        //master status
        String masterStatus = null;
        if (userSession.containsKey("master")){
            masterStatus = "online";
        } else {
            masterStatus = "offline";
        }
        return masterStatus;
    }

    private static String changeDateTimePattern(String pattern) {
        //sending time
        LocalDateTime currentTime = LocalDateTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern(pattern));
        return time;
    }



}
