package org.example.chat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AIChat {

    //Send user query to OpenRouter AI using java HttpClient.
    //https://openjdk.org/groups/net/httpclient/intro.html
    //https://openrouter.ai/docs/api-reference/overview
    //Add support for setting API-Key using dotenv-java
    //https://github.com/cdimascio/dotenv-java?tab=readme-ov-file
    //Convert json response to record Objects using Jackson and print the answer on screen.
    //https://github.com/FasterXML/jackson
    //Todo: Create a complete chat conversation by including previous messages in next query for context

    static void main() throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();

        //Todo: Prompt user for content.
        String prompt = """
                {
                	"model": "moonshotai/kimi-k2:free",
                	"messages": [
                	    {
                	        "role": "system",
                	        "content": "Svara kort helst inte med mer än 5 meningar."
                	    },
                		{
                			"role": "user",
                			"content": "Vad är meningen med livet?"
                		}
                	]
                }
                """;

        String apiKey = dotenv.get("OPENROUTER_API_KEY");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " +  apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ChatCompletion completion = mapper.readValue(json, ChatCompletion.class);

        System.out.println(completion.choices()[0].message().content());
    }
}

record ChatCompletion(Choice[] choices) {
}

record Choice(Message message) {
}

record Message(String role, String content, String refusal) {
}

