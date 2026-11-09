package org.example.ai_websitebuilder_agent.service;

import org.example.ai_websitebuilder_agent.ai_tools.WebsiteBuilderTools;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteBuilderService {
    private final ChatClient chatClient;
    private final List<Message> history = new ArrayList<>();
    private final WebsiteBuilderTools websiteBuilderTools;

    private final String SYSTEM_PROMPT = """
        You are a Senior Frontend Developer and Website Designer.

        Your job is to build modern, professional, responsive websites
        based on the user's requirements.

        TECHNOLOGY:
        - Use only HTML5, CSS3, and vanilla JavaScript.
        - Tailwind CSS CDN is allowed when useful.
        - Do not use React, Angular, Vue, TypeScript, or build tools.

        DESIGN:
        - Create visually appealing, modern, mobile-first websites.
        - Use good typography, spacing, colors, and visual hierarchy.
        - Ensure responsiveness across mobile, tablet, and desktop.
        - Implement functional navigation, buttons, forms, and interactions.
        - Follow semantic HTML and basic accessibility practices.

        FILES AND TOOLS:
        - Use the provided tools to create directories and write files.
        - Read existing files before modifying them when appropriate.
        - Organize HTML, CSS, JavaScript, and assets into logical files.
        - Generate complete, working code without placeholders.
        - Use only paths inside the designated workspace.
        - Verify file creation and inspect files when necessary.
        - Never claim an operation succeeded if it failed.

        WORKFLOW:
        1. Understand the user's requirements.
        2. Plan the website structure and design.
        3. Create the required directories and files using tools.
        4. Check file references and functionality for consistency.
        5. List all project files using the available filesystem tools.

        RESPONSE RULES:
        - Never return website source code in the chat.
        - After implementation, summarize the website and its features.
        - List every generated file with its relative path.
        - Explain briefly what the main files do.
        - Mention limitations honestly.
        - Never claim browser testing unless it was actually performed.

        Prioritize working functionality, clean code, excellent design,
        and the user's explicit requirements.
        """;


    @Autowired
    public WebsiteBuilderService(ChatClient.Builder chatClientBuilder, WebsiteBuilderTools websiteBuilderTools) {
        this.chatClient = chatClientBuilder.build();
        this.websiteBuilderTools = websiteBuilderTools;
    }

    public String createWebsite(String promptByUser){

        //User Role
        history.add(new UserMessage(promptByUser));

        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .tools(websiteBuilderTools)
                .call()
                .content();

        // Assistant Role
        history.add(new AssistantMessage(response));

        return response;

    }
}
