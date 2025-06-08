package org.jaspr.hr.demo;
import com.google.gson.Gson;

/**
 * Represents a response from the Ollama API.
 */
public class OllamaResponse {

        // The model name used to generate the response.
        private String model;
        // The timestamp when the response was created.
        private String created_at;
        // The actual text response from the API.
        private String response;
        // Status indicating whether the response generation is done.
        private String done;

    /**
     * Returns the text response from Ollama.
     * @return the response content as a String
     */
    public String getResponse() {
            return response;
        }

    /**
     * Parses a JSON string into an OllamaResponse object.
     * Ollama Documentation: https://github.com/ollama/ollama/blob/main/docs/api.md
     * @param body the JSON string response from the API
     * @return the parsed OllamaResponse object
     */
    public static OllamaResponse fromJson(String body) {
            //for documentation of how to decode JSON response https://github.com/ollama/ollama/blob/main/docs/api.md
            Gson gson = new Gson();
            OllamaResponse response = gson.fromJson(body, OllamaResponse.class);
            return response;
        }

}
