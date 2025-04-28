package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.OccasionDTO;
import com.example.backendhoatuoiuit.service.OccasionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/occasions")
public class OccasionController {

    @Autowired
    private OccasionService occasionService;

    @GetMapping
    public List<OccasionDTO> getAllOccasions() {
        return occasionService.getAllOccasions();
    }

    @GetMapping("/{id}")
    public OccasionDTO getOccasionById(@PathVariable Integer id) {
        return occasionService.getOccasionById(id);
    }

    @PostMapping
    public OccasionDTO createOccasion(@RequestBody OccasionDTO occasionDTO) {
        return occasionService.createOccasion(occasionDTO);
    }

    @PutMapping("/{id}")
    public OccasionDTO updateOccasion(@PathVariable Integer id, @RequestBody OccasionDTO occasionDTO) {
        return occasionService.updateOccasion(id, occasionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOccasion(@PathVariable Integer id) {
        occasionService.deleteOccasion(id);
    }
}
