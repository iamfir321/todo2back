package com.example.todobackver2.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String message;
    private int status;
    private String userName;
    private String email;
    private String avatar;
    private Long userId;
}
