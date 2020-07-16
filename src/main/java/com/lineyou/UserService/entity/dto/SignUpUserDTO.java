package com.lineyou.UserService.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Jun
 * @date 2020-07-16 10:41
 */
@Data
public class SignUpUserDTO {

    @Pattern(regexp = "1[1-9][0-9]{9}")
    @NotBlank
    private String mobile;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
