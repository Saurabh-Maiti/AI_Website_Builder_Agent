# AI Website Builder Agent

An AI-powered Website Builder Agent that generates websites from natural language prompts. Simply describe the website you want to create, and the AI agent processes your instructions to generate a website based on your requirements.

Built using **Java, Spring Boot, Spring AI, and OpenRouter**, this project leverages Large Language Models (LLMs) to transform user prompts into website-generation workflows.

The application is **not restricted to a specific AI model**. You can use any compatible model available through OpenRouter by changing the model configuration.

## Overview

The AI Website Builder Agent simplifies website creation by allowing users to describe their requirements using natural language instead of manually writing website code.

For example:

> Build a modern portfolio website for a software engineer with a dark theme, an about section, skills, projects, and a contact form.

The AI agent processes the prompt using an LLM and generates a website according to the given instructions.

The application uses NVIDIA Nemotron by default through OpenRouter, but users can switch to other available models without modifying the Java source code.

## Features

- **Prompt-Based Website Generation:** Generate websites using natural language commands.
- **AI-Powered Processing:** Uses Spring AI to integrate Large Language Models.
- **Model Flexibility:** Supports different compatible AI models available through OpenRouter.
- **OpenRouter Integration:** Connects to multiple AI model providers through a unified API.
- **Configurable AI Model:** Change the model through environment variables without modifying application code.
- **Modular Backend Architecture:** Organized using Controller, Service, and AI Tools layers.
- **Extensible Design:** Structured to support additional website-building capabilities.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Backend application framework |
| Spring AI | AI integration and orchestration |
| OpenRouter | LLM API gateway |
| NVIDIA Nemotron | Default AI model |
| Maven | Dependency management and build tool |

## Project Structure

```text
AI_WebsiteBuilder_Agent/
│
├── .idea/
│
├── .mvn/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/ai_websitebuilder_agent/
│   │   │       │
│   │   │       ├── ai_tools/
│   │   │       │   └── WebsiteBuilderTools.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── WebsiteBuilderController.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── WebsiteBuilderService.java
│   │   │       │
│   │   │       └── AiWebsiteBuilderAgentApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── target/
│
├── .env
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

### Core Components

**1. WebsiteBuilderController**

Handles incoming HTTP requests and acts as the entry point for website-building operations.

**2. WebsiteBuilderService**

Contains the business logic and coordinates the website-generation workflow. It acts as the bridge between the controller, AI model, and website-building tools.

**3. WebsiteBuilderTools**

Contains AI tools that provide website-building capabilities to the AI agent through Spring AI tool integration.

**4. AiWebsiteBuilderAgentApplication**

The main Spring Boot application class responsible for starting the application.

**5. application.properties**

Contains Spring Boot and Spring AI configuration, including API credentials, model settings, and external API configuration.

## Prerequisites

Before running the application, ensure you have:

- Java JDK (compatible with the Java version configured in `pom.xml`).
- Maven (or use the included Maven Wrapper).
- An OpenRouter API key.
- IntelliJ IDEA or another Java IDE.

## Getting Started

### 1. Clone the Repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd AI_WebsiteBuilder_Agent
```

### 2. Configure Environment Variables

Create a `.env` file in the project root directory.

```env
OPEN_ROUTER_API_KEY=your_openrouter_api_key
OPEN_ROUTER_MODEL_NAME=nvidia/nemotron-3.5-lightning:free
OPEN_ROUTER_BASE_URL=https://openrouter.ai/api/v1
```

Replace `your_openrouter_api_key` with your actual OpenRouter API key.

Get your API key here:

https://openrouter.ai/keys

### 3. Configure application.properties

Navigate to:

`src/main/resources/application.properties`

Add the following configuration:

```properties
spring.application.name=AI_WebsiteBuilder_Agent

# OpenRouter Configuration
spring.ai.openai.base-url=${OPEN_ROUTER_BASE_URL}
spring.ai.openai.api-key=${OPEN_ROUTER_API_KEY}

# AI Model Configuration
spring.ai.openai.chat.options.model=${OPEN_ROUTER_MODEL_NAME}
```

**Important:** Spring Boot does not automatically load standard `.env` files. You must export the environment variables before starting the application or configure them in your IDE.

### 4. Load Environment Variables

For macOS and Linux, open your terminal in the project root and execute:

```bash
set -a
source .env
set +a
```

This loads the variables from `.env` into the current terminal environment.

To verify:

```bash
echo $OPEN_ROUTER_MODEL_NAME
```

### 5. Run the Application

Using Maven Wrapper:

**macOS / Linux**

```bash
./mvnw spring-boot:run
```

**Windows**

```bash
mvnw.cmd spring-boot:run
```

Alternatively, open the project in IntelliJ IDEA and run:

`AiWebsiteBuilderAgentApplication.java`

If running directly from IntelliJ, configure the environment variables in Run → Edit Configurations → Environment Variables.

### 6. Verify Application Startup

Once the application starts successfully, Spring Boot launches its embedded Tomcat server.

Default local address:

```text
http://localhost:8080
```

## Changing the AI Model

The application is designed to support different compatible AI models available through OpenRouter.

You are not restricted to NVIDIA Nemotron.

To change the model, simply update the following variable in your `.env` file:

```env
OPEN_ROUTER_MODEL_NAME=your_preferred_model
```

For example:

**Default model:**

```env
OPEN_ROUTER_MODEL_NAME=nvidia/nemotron-3.5-lightning:free
```

**Another model:**

```env
OPEN_ROUTER_MODEL_NAME=provider/model-name
```

Replace the example with the exact model identifier available on OpenRouter.

Explore available models:

https://openrouter.ai/models

After changing the model, restart the application.

No Java code changes are required, provided the selected model is compatible with the OpenAI-compatible chat API and the Spring AI features used by the application.

## Configuration Reference

| Environment Variable | Description |
|---|---|
| `OPEN_ROUTER_API_KEY` | API key used to authenticate with OpenRouter |
| `OPEN_ROUTER_MODEL_NAME` | AI model used for website generation |
| `OPEN_ROUTER_BASE_URL` | OpenRouter API base URL |

## Security

- Never commit your `.env` file to GitHub.
- Never expose your OpenRouter API key in frontend code.
- Store API credentials in environment variables.
- Rotate API keys if they are accidentally exposed.
- Use `.gitignore` to exclude sensitive configuration files.

Example `.gitignore` entries:

```gitignore
.env
.env.*
!.env.example
target/
.idea/
```

## Future Enhancements

Potential improvements for the project:

- Live website preview.
- Support for multiple frontend frameworks.
- Iterative website modification through conversational prompts.
- Download generated website source code.
- Website templates and themes.
- Multi-agent architecture for planning, generation, and validation.
- Deployment of generated websites.

## Contributing

Contributions, suggestions, and feature requests are welcome.

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes.
4. Push your branch.
5. Open a Pull Request.

## Author

**Saurabh Maiti**

Software Engineer | Java | Spring Boot | AI Engineering

---

*Built with Java, Spring Boot, Spring AI, and OpenRouter.*
