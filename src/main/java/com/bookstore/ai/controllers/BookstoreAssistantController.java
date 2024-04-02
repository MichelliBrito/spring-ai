package com.bookstore.ai.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

    private final OpenAiChatClient chatClient;

    public BookstoreAssistantController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/informations")
    public String bookstoreChat(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
        return chatClient.call(message);
    }

//    @GetMapping("/informations")
//    public ChatResponse bookstoreChatEx2(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatClient.call(new Prompt(message));
//    }

    @GetMapping("/reviews")
    public String bookstoreReview(@RequestParam(value = "book", defaultValue = "Dom Quixote") String book) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                  Por favor, me forneça
                  um breve resumo do livro {book}
                  e também a biografia de seu autor.
                """);
        promptTemplate.add("book", book);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
        return chatClient.stream(message);
    }

//    @GetMapping("/stream/informations")
//    public Flux<ChatResponse> bookstoreChatStreamEx2(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatClient.stream(new Prompt(message));
//    }

}
