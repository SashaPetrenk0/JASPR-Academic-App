package org.jaspr.hr.demo;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for generating and parsing AI-generated multiple-choice questions
 * The questions are fetched from Ollama 3.2 via asynchronous call
 */
public class AIGenQuestions {

    /**
     * Inner class that implements the ResponseListener interface.
     * It handles the asynchronous response returned by the Ollama API,
     * extracts questions from the response string using regex, and prints them to the console.
     */
    static class MyResponseListener implements ResponseListener {

        /**
         * Called when a response is received from the Ollama API.
         * Parses the response text to extract question data and prints it.
         * @param response The response object containing the AI-generated question text.
         */
        @Override
        public void onResponseReceived(OllamaResponse response) {
            System.out.print("Ollama says: ");
            String input = response.getResponse();

            // Pattern to match the full question format including options and answer.
            Pattern fullQuestionPattern = Pattern.compile(
                    "Question \\d+:\\s*(.*?)\\s*" +           // Question text
                            "A\\)\\s*(.*?)\\s*" +                     // Option A
                            "B\\)\\s*(.*?)\\s*" +                     // Option B
                            "C\\)\\s*(.*?)\\s*" +                     // Option C
                            "D\\)\\s*(.*?)\\s*" +                     // Option D
                            "Answer:\\s*([A-D])\\)",                  // Correct answer
                    Pattern.DOTALL
            );

            Matcher matcher = fullQuestionPattern.matcher(input);
            List<Question> questions = new ArrayList<>();

            // Extract all questions from the response using the pattern.
            while (matcher.find()) {
                String qText = matcher.group(1).trim();
                String a = matcher.group(2).trim();
                String b = matcher.group(3).trim();
                String c = matcher.group(4).trim();
                String d = matcher.group(5).trim();
                String correct = matcher.group(6).trim();

                questions.add(new Question(qText, a, b, c, d, correct));
            }

            // Print each extracted question and its options.
            for (Question q : questions) {
                System.out.println("Q: " + q.getQuestion());
                System.out.println("a: " + q.getOptionA());
                System.out.println("b: " + q.getOptionB());
                System.out.println("c: " + q.getOptionC());
                System.out.println("d: " + q.getOptionD());
                System.out.println("ans: " + q.getCorrectAnswer());
                System.out.println("----");
            }
            System.out.println("----");



        }
    }



        public static void genQuestions(String prompt) {

            String apiURL = "http://127.0.0.1:11434/api/generate/";
            String model = "llama3.2";


            OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
            fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

            // note that fetcher returns immediately, and the answer is printed by MyResponseListener when a response becomes available
            System.out.println("======================================================");
            System.out.print("You asked: ");
            System.out.println(prompt);
            System.out.println("======================================================");


        }

}
