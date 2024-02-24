package com.pray.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * LoginFormDto
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@Data
@Getter
@Setter
public class LoginFormDto {
    String username;
    String password;
    String code;
}
