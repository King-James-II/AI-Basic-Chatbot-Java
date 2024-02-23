package chatbot;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A basic chatbot program that interacts with users by providing responses based on predefined phrases
 * and allows users to add new responses during the conversation.
 */
public class BasicChatbot {
    // Path to the file containing chatbot responses
    private static final String RESPONSES_FILE = "chatbot_responses.txt";

    public static void main(String[] args) {
        // Load responses from the responses file
        Map<String, String> responses = loadResponses();

        // Initialize scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chatbot: Hi! How can I help you? (type 'exit' to quit)");

        // Main loop for chatting with the user
        while (true) {
            System.out.print("You: ");
            String userMessage = scanner.nextLine().trim().toLowerCase();

            // Exit condition if user types 'exit'
            if (userMessage.equalsIgnoreCase("exit")) {
                System.out.println("Chatbot: Goodbye!");
                break;
            }

            // Retrieve response for the user's message from the map of responses
            String botResponse = responses.getOrDefault(userMessage, "Chatbot: Sorry, I don't have a response for that.");
            System.out.println("Chatbot: " + botResponse);

            // If the user's message is not in the responses, prompt the user to provide a response
            if (!responses.containsKey(userMessage)) {
                System.out.print("Chatbot: Please provide a response for this phrase: ");
                String userResponse = scanner.nextLine().trim();
                // Add the user's response to the map of responses
                responses.put(userMessage, userResponse);
                // Save the updated responses to the file
                saveResponses(responses);
                System.out.println("Chatbot: Response added. Thank you!");
            }
        }
    }

    /**
     * Load responses from the responses file and store them in a map.
     */
    private static Map<String, String> loadResponses() {
        Map<String, String> responses = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESPONSES_FILE))) {
            String line;
            // Read each line from the file and split it into key-value pairs
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                // Add key-value pairs to the map
                if (parts.length == 2) {
                    responses.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }

    /**
     * Save responses to the responses file.
     */
    private static void saveResponses(Map<String, String> responses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESPONSES_FILE))) {
            // Write each response key-value pair to the file
            for (Map.Entry<String, String> entry : responses.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
