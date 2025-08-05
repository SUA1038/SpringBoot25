package org.mbc.board.shop.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mbc.board.entity.Item;
import org.mbc.board.entity.Order;
import org.mbc.board.entity.OrderItem;
import org.mbc.board.entity.constant.ItemSellStatus;
import org.mbc.board.repository.ItemRepository;
import org.mbc.board.repository.OrderItemRepository;
import org.mbc.board.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class OrderTests {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){

        Order order = new Order();

        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);   // 아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담아줍니다.
        }

        orderRepository.saveAndFlush(order);    // order엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영
        em.clear(); // 영속성 컨텍스트 상태 초기화

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        // 영속성 컨텍스트를 초기화했기 때문에 데이터베이스에서 주문 엔티티를 조회. select 쿼리문이 실행되는 것을 콘솔창에서 확인 가능.
        assertEquals(3, savedOrder.getOrderItems().size());
        // itemOrder 엔티티 3개가 실제로 데이터베이스에 저장되었는지 검사


    }


    public Order createOrder(){
        Order order = new Order();
        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        orderRepository.saveAndFlush(order);
        em.clear();
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
        return savedOrder;
    }


    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // order 엔티티에서 관리하고있는  orderItem 리스트의 0번째 인덱스 요소 제거
        em.flush();
    }


    @Autowired
    OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        // 영속성 컨텍스트의 상태 초기화 후 order 엔티티에 저장했던 주문 상품 아이디를 이용하여 orderItem을 데이터베이스에서 다시 조회.
        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("========================");
        orderItem.getOrder().getOrderDate();
        System.out.println("========================");
        // orderItem 엔티티에 있는 order 객체의 클래스 출력.
    }

}
