package com.example.jpa_todo.common;

public interface Const {
    String LOGIN_USER = "loginUser";
    String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,}$";
    String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$";
}
