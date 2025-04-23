package org.jaspr.hr.demo;

public class AIGenQuestions {
    static class MyResponseListener implements ResponseListener {

        @Override
        public void onResponseReceived(OllamaResponse response) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print("Ollama says: ");
            System.out.println(response.getResponse());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    };


    //trying ai implementation

        public static void genQuestions(String prompt) {

            String apiURL = "http://127.0.0.1:11434/api/generate/";
            String model = "llama3.2";
            //String prompt = "Write 2 multiple choice questions about chemistry stored in a java array";

            OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
            fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

            // note that fetcher returns immediately, and the answer is printed by MyResponseListener when a response becomes available
            System.out.println("======================================================");
            System.out.print("You asked: ");
            System.out.println(prompt);
            System.out.println("======================================================");
        }

}
