package com.stepashka.bd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ClothingSize {
    private Long id;
    private String code;
    private String note;
   }
