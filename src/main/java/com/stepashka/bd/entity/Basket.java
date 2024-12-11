package com.stepashka.bd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Basket {
    private Long id;
    private Long itemCount;
    private Clothes item;
    private Stock stock;
}
