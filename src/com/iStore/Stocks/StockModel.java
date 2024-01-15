package com.iStore.Stocks;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Description -> StockModel class
 *
 * @Author benjamin, Pierre et Léonard
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class StockModel {

    private Long id;
    private Long produit_id;
    private Long quantite;
    private Long entreprise_id ;

}
