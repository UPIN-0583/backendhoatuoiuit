package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.PromotionDTO;
import com.example.backendhoatuoiuit.entity.Promotion;
import com.example.backendhoatuoiuit.mapper.PromotionMapper;
import com.example.backendhoatuoiuit.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    public List<PromotionDTO> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream().map(promotionMapper::toDTO).collect(Collectors.toList());
    }

    public PromotionDTO getPromotionById(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return promotionMapper.toDTO(promotion);
    }

    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDTO(promotion);
    }

    public PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setCode(promotionDTO.getCode());
        promotion.setDiscountValue(promotionDTO.getDiscountValue());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotion.setIsActive(promotionDTO.getIsActive());
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDTO(promotion);
    }

    public void deletePromotion(Integer id) {
        promotionRepository.deleteById(id);
    }
}
