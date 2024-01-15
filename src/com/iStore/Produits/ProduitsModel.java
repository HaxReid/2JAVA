package com.iStore.Produits;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Description -> ProduitsModel class
 *
 * @Author benjamin, Pierre et Léonard
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ProduitsModel {
    private Long id;
    private String name;
    private Long price;
    private String description;

}
