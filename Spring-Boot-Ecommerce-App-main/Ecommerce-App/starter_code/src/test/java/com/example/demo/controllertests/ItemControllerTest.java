package com.example.demo.controllertests;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp(){
        try{
            try{
                itemController = new ItemController();
                TestUtils.injectObjects(itemController, "itemRepository", itemRepository);

                new Item().setId(1L);
                new Item().setName("round widget");

                new Item().setPrice(BigDecimal.valueOf(2.99));
                new Item().setDescription("A round widget");
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

            when(itemRepository.findAll()).thenReturn(Collections.singletonList(new Item()));
            when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(new Item()));
            when(itemRepository.findByName("round widget")).thenReturn(Collections.singletonList(new Item()));

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void getItems(){
        try{
            assertNotNull(itemController.getItems());
            assertEquals(200, itemController.getItems().getStatusCodeValue());
            assertTrue(itemController.getItems().getBody().size() > 0);

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void getItemsByIdHappyPath(){

        try{
            try{
                assertNotNull(itemController.getItemById(1L));

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(200, itemController.getItemById(1L).getStatusCodeValue());
            assertNotNull(itemController.getItemById(1L).getBody());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void getItemsByNameHappyPath(){
        try{
            try{
                assertNotNull(itemController.getItemsByName("round widget"));

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(200, itemController.getItemsByName("round widget").getStatusCodeValue());
            assertTrue(itemController.getItemsByName("round widget").getBody().size() > 0);

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }


    @Test
    public void getItemsByIdNegativeTest(){
        try{
            try{
                assertNotNull(itemController.getItemById(2L));

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertEquals(404, itemController.getItemById(2L).getStatusCodeValue());
            assertNull(itemController.getItemById(2L).getBody());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void getItemsByNameNegativeTest(){

        try{
            assertNotNull(itemController.getItemsByName("round"));
            assertEquals(404, itemController.getItemsByName("round").getStatusCodeValue());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }
}
