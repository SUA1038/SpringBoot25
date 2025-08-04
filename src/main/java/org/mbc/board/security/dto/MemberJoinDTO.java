package org.mbc.board.security.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MemberJoinDTO {
    // 프론트에서 처리되는 객체를 구현한다.

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

}

