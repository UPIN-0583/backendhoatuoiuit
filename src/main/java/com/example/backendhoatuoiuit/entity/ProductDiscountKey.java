package com.example.backendhoatuoiuit.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ProductDiscountKey implements Serializable {
    private Integer productId;
    private Integer discountId;
}
