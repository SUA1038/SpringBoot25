package org.mbc.board.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mbc.board.domain.Member;

@Getter
@Setter
public class MemberFormDTO {

    Member member;


}
