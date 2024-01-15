package com.iStore.Users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Description -> UserModel class
 *
 * @Author benjamin, Pierre et Léonard
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private String email;
    private Boolean is_admin;
    private String password;



    public UserModel() {

    }
}