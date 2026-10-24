package org.example.ai_websitebuilder_agent.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteBuilderService {
    private final ChatClient chatClient;
    private final List<Message> history = new ArrayList<>();
    private final String SYSTEM_PROMPT = "";
    public WebsiteBuilderService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String createWebsite(String promptByUser){

        //User Role
        history.add(new UserMessage(promptByUser));

        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .call()
                .content();

        // Assistant Role
        history.add(new AssistantMessage(response));

        return response;

    }
}
