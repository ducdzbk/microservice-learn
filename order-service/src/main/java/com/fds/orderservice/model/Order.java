package com.fds.orderservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_order")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @Column(name = "product_order_id")
    private Long id;
    private String orderNumber;

  // @OneToMany(cascade = CascadeType.ALL)
  // @JoinColumn(name = "t_order_Line_Items")

   @OneToMany( cascade = CascadeType.ALL)
   private List<OrderLineItems> orderLineItemsList;
}
