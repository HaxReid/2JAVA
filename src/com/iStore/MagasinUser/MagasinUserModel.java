package com.iStore.MagasinUser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Description -> MagasinUserModel class
 *
 * @Author benjamin, Pierre et Léonard
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor

public class MagasinUserModel {
    private Long id;
    private Long user_id;
    private Long magasin_id;
    private String role;
}
