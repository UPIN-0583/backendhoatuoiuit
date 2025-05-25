package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.ChatRequest;
import com.example.backendhoatuoiuit.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/suggest")
    public ResponseEntity<?> suggest(@RequestBody ChatRequest request) {
        List<Map<String, Object>> response = chatbotService.generateFinalResponse(request.getQuestion());
        return ResponseEntity.ok(Map.of("answer", response));
    }

}
