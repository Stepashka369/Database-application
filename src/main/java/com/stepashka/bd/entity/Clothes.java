package com.stepashka.bd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Clothes {
    private Long id;
    private Float cost;
    private String note;
    private ClothingSize size;
    private ClothingType type;
    private ClothingBrand brand;
}
