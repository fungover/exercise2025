package org.example.chat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AIChat {

    //Todo: Send user query to OpenRouter AI using java HttpClient.
    //https://openjdk.org/groups/net/httpclient/intro.html
    //https://openrouter.ai/docs/api-reference/overview
    //Todo: Add support for setting API-Key using dotenv-java
    //https://github.com/cdimascio/dotenv-java?tab=readme-ov-file
    //Todo: Convert json response to record Objects using Jackson and print the answer on screen.
    //https://github.com/FasterXML/jackson
    //Todo: Create a complete chat conversation by including previous messages in next query for context

    static void main() throws IOException, InterruptedException {
        String prompt = """
                {
                	"model": "deepseek/deepseek-chat-v3-0324:free",
                	"messages": [
                		{
                			"role": "user",
                			"content": "Vad är meningen med livet?"
                		}
                	]
                }
                """;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + System.getenv("OPENROUTER_API_KEY"))
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
