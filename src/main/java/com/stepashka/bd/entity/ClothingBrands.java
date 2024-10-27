package com.stepashka.bd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ClothingBrands {
    private Long id;
    private String code;
    private String note;
}