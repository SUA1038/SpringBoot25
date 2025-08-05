package org.mbc.board.shop.entity;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mbc.board.domain.Member;
import org.mbc.board.entity.Cart;
import org.mbc.board.repository.CartRepository;
import org.mbc.board.repository.MemberRepository;
import org.mbc.board.security.dto.MemberJoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        memberJoinDTO.setMid("TEST_USER");
        memberJoinDTO.setMpw(passwordEncoder.encode("1234"));  // 비밀번호는 인코딩
        memberJoinDTO.setEmail("test@test.com");

        return Member.builder()
                .mid(memberJoinDTO.getMid())
                .mpw(memberJoinDTO.getMpw())
                .email(memberJoinDTO.getEmail())
                .del(false)
                .social(false)
                .build();

    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest(){
        // 1. 멤버 만들고 저장
        Member member = createMember();
        memberRepository.save(member); // 꼭 먼저 저장해야 함!

        // 2. 장바구니 만들고, 멤버 설정 후 저장
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart); // 멤버가 먼저 저장돼 있어야 함

        // 3. 영속성 컨텍스트 비움
        em.flush();
        em.clear();

        // 4. DB에서 다시 조회
        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 5. 검증
        assertEquals(savedCart.getMember().getMid(), member.getMid());
    }
}
