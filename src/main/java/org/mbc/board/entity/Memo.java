package org.mbc.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 데이터베이스 관련 객체임을 선언한다.
@Table(name="tbl_memo") // 데이터베이스 테이블명을 선언한다.
@ToString
@Getter
@Builder // 빌터패턴 사용 member.setName(), member.getName() -> member.name()
@AllArgsConstructor // 모든 필드값을 이용해서 생성자
@NoArgsConstructor  // 기본 생성자
public class Memo {
    
    @Id // pk임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GenerationType.IDENTITY -> pk를 자동으로 생성하고자 함.(키생성전략)
    // 만일 연결되는 데이터베이스가 오라클이면 번호를 위한 별도의 테이블을 생성(시퀀스 객체)
    // Mysql이나 mariaDB면 auto increment를 기본으로 사용해서 새로운 레코드가 기록될 때 다른 번호를 줌.
    // GenerationType.AUTO -> jpa가 알아서 생성방식을 결정
    // GenerationType.SEQUENCE -> 데이터베이스의 sequence를 이용해서 키를 생성
    // GenerationType.TABLE -> 키 생성 전용 테이블을 생성해서 키를 생성
    private Long mno;

    @Column(length = 200, nullable = false) // 200글자에 notnull 효과
    private String memoText;

}
