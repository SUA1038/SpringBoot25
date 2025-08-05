package org.mbc.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mbc.board.domain.Member;
import org.mbc.board.entity.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    // p210 추가, 고아 객체 제거
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // CascadeType.ALL : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이함

    // 주문 상품 엔티티와 일대다 매핑.
    // 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티이다.
    // Order 엔티티가 주인이 아니므로 mappedBy 속성으로 연관 관계의 주인을 설정
    // 속성 값으로 order를 적은 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미
    // 즉, 연관 관계의 주인인 필드를 order를 mappedBy 값으로 세팅하면 된다.

    private List<OrderItem> orderItems = new ArrayList<>();
    // 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑.

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
