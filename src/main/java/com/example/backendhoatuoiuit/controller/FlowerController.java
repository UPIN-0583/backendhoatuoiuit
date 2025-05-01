package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.FlowerDTO;
import com.example.backendhoatuoiuit.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flowers")
public class FlowerController {

    @Autowired
    private FlowerService flowerService;

    @GetMapping
    public List<FlowerDTO> getAllFlowers() {
        return flowerService.getAllFlowers();
    }

    @GetMapping("/{id}")
    public FlowerDTO getFlowerById(@PathVariable Integer id) {
        return flowerService.getFlowerById(id);
    }

    @PostMapping
    public FlowerDTO createFlower(@RequestBody FlowerDTO flowerDTO) {
        return flowerService.createFlower(flowerDTO);
    }

    @PutMapping("/{id}")
    public FlowerDTO updateFlower(@PathVariable Integer id, @RequestBody FlowerDTO flowerDTO) {
        return flowerService.updateFlower(id, flowerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFlower(@PathVariable Integer id) {
        flowerService.deleteFlower(id);
    }

    @GetMapping("/active")
    public List<FlowerDTO> getActiveFlowers() {
        return flowerService.getActiveFlowers();
    }
}
