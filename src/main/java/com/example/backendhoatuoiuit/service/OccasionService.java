package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.OccasionDTO;
import com.example.backendhoatuoiuit.entity.Occasion;
import com.example.backendhoatuoiuit.mapper.OccasionMapper;
import com.example.backendhoatuoiuit.repository.OccasionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OccasionService {

    @Autowired
    private OccasionRepository occasionRepository;

    @Autowired
    private OccasionMapper occasionMapper;

    public List<OccasionDTO> getAllOccasions() {
        List<Occasion> occasions = occasionRepository.findAll();
        return occasions.stream().map(occasionMapper::toDTO).collect(Collectors.toList());
    }

    public OccasionDTO getOccasionById(Integer id) {
        Occasion occasion = occasionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Occasion not found"));
        return occasionMapper.toDTO(occasion);
    }

    public OccasionDTO createOccasion(OccasionDTO occasionDTO) {
        Occasion occasion = occasionMapper.toEntity(occasionDTO);
        occasion = occasionRepository.save(occasion);
        return occasionMapper.toDTO(occasion);
    }

    public OccasionDTO updateOccasion(Integer id, OccasionDTO occasionDTO) {
        Occasion occasion = occasionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Occasion not found"));
        occasion.setName(occasionDTO.getName());
        occasion.setDescription(occasionDTO.getDescription());
        occasion.setIsActive(occasionDTO.getIsActive());
        occasion = occasionRepository.save(occasion);
        return occasionMapper.toDTO(occasion);
    }

    public void deleteOccasion(Integer id) {
        occasionRepository.deleteById(id);
    }
}
