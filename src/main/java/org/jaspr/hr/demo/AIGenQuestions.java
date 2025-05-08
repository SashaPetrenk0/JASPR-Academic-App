package org.jaspr.hr.demo;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AIGenQuestions {
    static class MyResponseListener implements ResponseListener {

        @Override
        public void onResponseReceived(OllamaResponse response) {
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.print("Ollama says: ");
//            System.out.println(response.getResponse());
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print("Ollama says: ");
            String input = response.getResponse();
            //TODO: create thread so that this stuff can be accessed
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

            while (matcher.find()) {
                String qText = matcher.group(1).trim();
                String a = matcher.group(2).trim();
                String b = matcher.group(3).trim();
                String c = matcher.group(4).trim();
                String d = matcher.group(5).trim();
                String correct = matcher.group(6).trim();

                questions.add(new Question(qText, a, b, c, d, correct));
            }

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
