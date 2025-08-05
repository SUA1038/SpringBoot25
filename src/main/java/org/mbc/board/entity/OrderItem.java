package org.mbc.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;  // 하나의 상품은 여러 주문이 들어갈 수 있으므로 주문 상품 기준으로 단방향 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;    // 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티와 주문 엔티티들 다대일 단방향 매핑

    private int orderPrice; // 주문 가격

    private int count;  // 수량

    /* P228 삭제

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    */

}
