package com.iStore.Magasin;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Description -> MagasinModel class
 *
 * @Author benjamin, Pierre et Léonard
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor

public class MagasinModel {
    private Long id;
    private String name;
}
