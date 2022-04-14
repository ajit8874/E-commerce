package com.example.demo.controllertests;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderConttrollertest {


    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);


    @Before
    public void setUp(){
        try{
            orderController = new OrderController();
            TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
            TestUtils.injectObjects(orderController, "userRepository", userRepository);

            new Item().setId(1L);
            new Item().setName("round widget");
            new Item().setPrice(BigDecimal.valueOf(2.99));
            new Item().setDescription("A round widget");
            List<Item> items = new ArrayList<Item>();
            items.add(new Item());

            try{
                User user = new User();
                Cart cart = new Cart();
                user.setId(0);
                user.setUsername("ajit");
                user.setPassword("8874");
                cart.setId(0L);
                cart.setUser(user);
                cart.setItems(items);
                cart.setTotal(BigDecimal.valueOf(2.99));
                user.setCart(cart);
                when(userRepository.findByUsername("ajit")).thenReturn(user);
                when(userRepository.findByUsername("someoneelse")).thenReturn(null);
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }


        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }


    @Test
    public void orderSubmitHappyPath(){
        try{
            try{
                assertNotNull(orderController.submit("ajit"));
                assertEquals(200, orderController.submit("ajit").getStatusCodeValue());
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertNotNull(orderController.submit("ajit").getBody());
            assertTrue(orderController.submit("ajit").getBody().getItems().size() == 1);

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void orderSubmitNegativeTest(){
        try{
            assertNotNull(orderController.submit("singh"));
            try{
                assertEquals(404, orderController.submit("singh").getStatusCodeValue());

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void getOrdersForUserHappyPath(){
        try{
            try{
                assertNotNull(orderController.getOrdersForUser("ajit"));

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(200, orderController.getOrdersForUser("ajit").getStatusCodeValue());
            assertNotNull(orderController.getOrdersForUser("ajit").getBody());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void getOrdersForUserNegativeTest(){
        try{
            try{
                assertNotNull(orderController.getOrdersForUser("singh"));

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(404, orderController.getOrdersForUser("singh").getStatusCodeValue());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }
}
