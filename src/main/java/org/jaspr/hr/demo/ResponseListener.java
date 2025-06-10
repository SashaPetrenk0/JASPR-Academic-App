package org.jaspr.hr.demo;

/**
 * A listener interface for receiving responses from an asynchronous process,
 * waiting for the ollama LLM to generate a response
 */
public interface ResponseListener {
     /**
      * Called when a response is recieved from the ollama
      * @param response the response from the LLM
      */
     void onResponseReceived(OllamaResponse response);
}
