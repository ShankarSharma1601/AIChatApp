package com.ai.ai_chat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QnAService {
	
	// Access to APIKey and URL
	@Value("${aimodel.api.url}")
	private String aiModelApiUrl;
	
	@Value("${aimodel.api.key}")
	private String aiModelApiKey;
	
	private final WebClient webClient;

	public QnAService(WebClient.Builder webClient) {
		this.webClient = webClient.build();
	}

	public String getAnswer(String question) {
		// Construct the request payload
		Map<String , Object> requestBody = Map.of(
			"contents" , new Object[] {
				    Map.of("parts" , new Object[] {
				    		Map.of("text" , question)
				    })
			}
		);
		
		// Make API call
		String response = webClient.post()
		         .uri(aiModelApiUrl + aiModelApiKey)
		         .header("Content-Type", "application/json")
		         .bodyValue(requestBody)
		         .retrieve()
		         .bodyToMono(String.class)
		         .block();
		
		// Return response
		return response;
	}

}
