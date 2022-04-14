package com.example.demo.controllertests;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {


    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);


    @Before
    public void setUp(){
        try{
            userController = new UserController();
            TestUtils.injectObjects(userController, "userRepository", userRepository);
            TestUtils.injectObjects(userController, "cartRepository", cartRepository);
            TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

            User user = new User();
            Cart cart = new Cart();
            user.setId(0);
            user.setUsername("ajit");
            user.setPassword("8874");
            user.setCart(cart);
            try{
                when(userRepository.findByUsername("ajit")).thenReturn(user);
                when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
                when(userRepository.findByUsername("singh")).thenReturn(null);
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void createUserHappyPath(){

        try{
            CreateUserRequest r = new CreateUserRequest();
            r.setUsername("ajit");
            r.setPassword("8874");
            r.setConfirmpassword("8874");


            try{
                final ResponseEntity<User> response = userController.createUser(r);
                assertNotNull(response);
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void createUserNegativeTest(){
        try{
            CreateUserRequest r = new CreateUserRequest();
            r.setUsername("ajit");
            r.setPassword("8874");
            r.setConfirmpassword("8874");


           try{
               assertNotNull(userController.createUser(r));
               assertEquals(400, userController.createUser(r).getStatusCodeValue());
           }catch (IllegalArgumentException ex){
               ex.printStackTrace();
           }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void confirmPassNotEqualNegativeTest(){

        try{
            CreateUserRequest r = new CreateUserRequest();
            r.setUsername("ajit");
            r.setPassword("8874");
            r.setConfirmpassword("8874");


            try{
                assertNotNull(userController.createUser(r));
                assertEquals(400, userController.createUser(r).getStatusCodeValue());
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserHappyPath(){
        try{
            assertNotNull(userController.findByUserName("ajit"));
            assertEquals(200, userController.findByUserName("ajit").getStatusCodeValue());
            assertEquals("ajit", userController.findByUserName("ajit").getBody().getUsername());

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void getUserNegativeTest(){
        try{
            try{
                assertEquals(404, userController.findByUserName("singh").getStatusCodeValue());

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertNotNull(userController.findByUserName("singh"));

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void getUserByIDHappyPath(){
        try{
            assertNotNull(userController.findById(0l));
            try{
                assertEquals(200, userController.findById(0l).getStatusCodeValue());
                assertEquals(0, userController.findById(0l).getBody().getId());
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserByIDNegativeTest(){
        try{
            try{
                assertEquals(404, userController.findById(1L).getStatusCodeValue());

            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            assertNotNull(userController.findById(1L));

        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

    }
}
