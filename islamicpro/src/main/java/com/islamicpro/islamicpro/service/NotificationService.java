package com.islamicpro.islamicpro.service;

import com.google.firebase.database.*;
import com.google.firebase.messaging.*;
import com.islamicpro.islamicpro.model.NotificationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    Logger log = LoggerFactory.getLogger(NotificationService.class);

//    @Value( "${firebase.notification.server.key}" )
//    String firebaseServerKey;

//    @Autowired
//    RestTemplate restTemplate;

    public void sendNotification(NotificationData notificationData){
        List<String> tokenList =  new ArrayList<>();
        DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference("islamicpro");
        final DatabaseReference tokens = defaultDatabase.child("tokens");
        tokens.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    tokenList.add(child.getValue(String.class));
                }
                startSendingNotification(tokenList,notificationData);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                log.debug("error while sending notification"+databaseError.getDetails());
            }
        });
    }

    private void startSendingNotification(final List<String> tokenList, final NotificationData notificationData) {
//        String url = "https://fcm.googleapis.com/fcm/send";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "key="+firebaseServerKey);
//        headers.set("Content-Type", "application/json");
//        HttpEntity<String> request = new HttpEntity<>(String, headers);
////        URI uri = new URI(url);
//        restTemplate.postForEntity(url,request,NotificationData.class);
        MulticastMessage message = MulticastMessage.builder()
                .putData("title", "Dua of the day: "+notificationData.getTitle())
                .putData("arabicText", notificationData.getArabicText())
                .putData("pronounciation", notificationData.getPronounciation())
                .putData("englishExplanation", notificationData.getEnglishExplanation())
                .putData("source", notificationData.getSource())
                .addAllTokens(tokenList)
                .build();
        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            List<String> failedTokens = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    // The order of responses corresponds to the order of the registration tokens.
                    failedTokens.add(tokenList.get(i));
                }
            }
            log.debug(response.getFailureCount() + " messages were failed to send for the tokens:" +failedTokens);
        }

// See the BatchResponse reference documentation
// for the contents of response.
        log.debug(response.getSuccessCount() + " messages were sent successfully");
    }

}
