package org.jaspr.hr.demo;

public class AIGenQuestions {


    //trying ai implementation

        public static void genQuestions(String prompt) {

            String apiURL = "http://127.0.0.1:11434/api/generate/";
            String model = "llama3.2";
            //String prompt = "Write 2 multiple choice questions about chemistry stored in a java array";

            OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);

            OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

            System.out.println("======================================================");
            System.out.print("You asked: ");
            System.out.println(prompt);
            System.out.println("======================================================");
            System.out.print("Ollama says: ");
            System.out.println(response.getResponse());
            System.out.println("======================================================");
        }

}
