package com.stepashka.bd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Clothes {
    private Long id;
    private Float cost;
    private String note;
    private Long sizeId;
    private Long typeId;
    private Long brandId;
}
