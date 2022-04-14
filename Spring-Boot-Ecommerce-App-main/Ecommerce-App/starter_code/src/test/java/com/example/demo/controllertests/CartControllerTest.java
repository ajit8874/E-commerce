package com.example.demo.controllertests;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setUp(){

        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);


        try{
            try{
                new User().setId(0);
                new User().setUsername("ajit");
                new User().setPassword("8874");
                User user = new User();
                user.setCart(new Cart());
                when(userRepository.findByUsername("ajit")).thenReturn(user);

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

            new Item().setId(1L);
            new Item().setName("Round Widget");
            Item item = new Item();
            item.setPrice(BigDecimal.valueOf(2.99));
            item.setDescription("A round widget");
            when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void addToCartHappyPath(){
        try {
            try{
                ModifyCartRequest r = new ModifyCartRequest();
                r.setUsername("ajit");
                r.setItemId(1L);
                r.setQuantity(1);
                assertNotNull(cartController.addTocart(r));
                assertEquals(200, cartController.addTocart(r).getStatusCodeValue());
                assertTrue(cartController.addTocart(r).getBody().getItems().size() > 0);


            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void addToCartNegativeTest(){
        try{
            try{
                new ModifyCartRequest().setUsername("singh");
                new ModifyCartRequest().setItemId(1L);
                new ModifyCartRequest().setQuantity(1);
                assertNotNull(cartController.addTocart(new ModifyCartRequest()));
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(404, cartController.addTocart(new ModifyCartRequest()).getStatusCodeValue());
            assertNull(cartController.addTocart(new ModifyCartRequest()).getBody());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void removeFromCartHappyPath(){

        try{

            ModifyCartRequest r = new ModifyCartRequest();
            r.setUsername("ajit");
            r.setItemId(1L);
            r.setQuantity(1);
            assertNotNull(cartController.addTocart(r));
            assertEquals(200, cartController.addTocart(r).getStatusCodeValue());
            assertNotNull(cartController.addTocart(r).getBody());
            try{
                assertNotNull(cartController.removeFromcart(r));
                assertEquals(200, cartController.removeFromcart(r).getStatusCodeValue());
                assertNotNull(cartController.removeFromcart(r).getBody());

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void removeFromCartNegativeTest(){
        try{
            new ModifyCartRequest().setUsername("ajit");
            new ModifyCartRequest().setItemId(3L);
            new ModifyCartRequest().setQuantity(1);

            try{
                assertNotNull(cartController.removeFromcart(new ModifyCartRequest()));
                assertEquals(404, cartController.removeFromcart(new ModifyCartRequest()).getStatusCodeValue());
                assertNull(cartController.removeFromcart(new ModifyCartRequest()).getBody());
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }


        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

}
