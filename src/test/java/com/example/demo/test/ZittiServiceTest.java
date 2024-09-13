package com.example.demo.test;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.Entity.ShoppingCart;
import com.example.demo.Exception.InvalidCommandException;
import com.example.demo.respository.ShoppingCartRepository;
import com.example.demo.service.ZittiService;

import ch.qos.logback.core.boolex.Matcher;
import jakarta.validation.constraints.AssertFalse.List;

public class ZittiServiceTest {
	@InjectMocks
	ZittiService zittiService;
	
	@Mock
	ShoppingCartRepository shoppingCartRepository;
	
	/*
	 * @Test
	 *  public void testGenerateUniqueId_WhenNoRecordsExist() {
	 * when(shoppingCartRepository.findTopByOrderByIdDesc()).thenReturn(Optional.
	 * empty()); int id = zittiService.generateUniqueId(); assertEquals(1, id); }
	 */
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.openMocks(this);
	}
   @Test
	public void testGenerateUniqueId_WhenNoRecordExist() {
		when(shoppingCartRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());
		int id = zittiService.generateUniqueId();
		assertEquals(1,id);
	}
	
	@Test
    public void testGenerateUniqueId_WhenRecordsExist() {
        ShoppingCart lastCart = new ShoppingCart(5, "user", new ArrayList<>());
        when(shoppingCartRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(lastCart));
        int id = zittiService.generateUniqueId();
        assertEquals(6, id);
    }
	@Test
    public void testProcessCommand_ValidCommand() {
        String username = "testUser";
        String command = "Hey. How are you?";
        String expectedResponse = "Hello, I am doing great.";

        String response = zittiService.processCommand(username, command);

        assertEquals(expectedResponse, response);
    }

	 @Test
	    public void testProcessCommand_InvalidCommand() {
	        String username = "testUser";
	        String command = "Unknown command";
	        Exception exception = assertThrows(InvalidCommandException.class, () -> {
	            zittiService.processCommand(username, command);
	        });

	        assertEquals("Hmm. I don't know that:Unknown command", exception.getMessage());
	    }

	

	    @Test
	    public void testAddToShoppingCart_NewCart() {
	        String username = "testUser";
	        String command = "Add Laptop to my shopping list.";
	        ShoppingCart newCart = new ShoppingCart(zittiService.generateUniqueId(), username, new ArrayList<>());

	      
	        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.empty());
	        when(shoppingCartRepository.save((Matcher) any(ShoppingCart.class))).thenReturn(newCart); 
	        String response = zittiService.addToShoppingCart(username, command);

	        // Assertions
	        assertEquals("Laptop added to your shopping list", response);
	        verify(shoppingCartRepository).save((Matcher) any(ShoppingCart.class)); // Use any(ShoppingCart.class) in verification
	    }

    @Test
    public void testAddToShoppingCart_ExistingCart() {
        String username = "testUser";
        String command = "Add Laptop to my shopping list.";
        ShoppingCart existingCart = new ShoppingCart(1, username, new ArrayList<>());

        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(existingCart));

        String response = zittiService.addToShoppingCart(username, command);

        assertEquals("Laptop added to your shopping list", response);
        assertTrue(existingCart.getItems().contains("Laptop"));
        verify(shoppingCartRepository).save(existingCart);
    }

    @Test
    public void testReadShoppingList_EmptyCart() {
        String username = "testUser";
        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(new ShoppingCart(username, new ArrayList<>())));

        String response = zittiService.readShoppingList(username);

        assertEquals("Your shopping list is empty.", response);
    }

    

    @Test
    public void testReadShoppingList_NonEmptyCart() {
        String username = "testUser";
        ShoppingCart cart = new ShoppingCart(username, Arrays.asList("Milk", "Soap"));
       when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(cart));
       String response = zittiService.readShoppingList(username);
       assertEquals("Here is your shopping list: Milk, Soap", response);
    }

    @Test
    public void testDeleteShoppingCart() {
        String username = "testUser";
        ShoppingCart cart = new ShoppingCart(username, new ArrayList<>());
        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(cart));
        zittiService.deleteShoppingCart(username);
        verify(shoppingCartRepository).delete(cart);
    }

   

    @Test
    public void testUpdateShoppingCart_AddItems() {
        String username = "testUser";
        ShoppingCart cart = new ShoppingCart(username, new ArrayList<>());
        
        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(cart));
        String response = zittiService.updateShoppingCart(username, Arrays.asList("Milk", "Eggs"), false);
        assertEquals("Shopping cart updated for user: testUser", response);
        assertTrue(cart.getItems().contains("Milk"));
        assertTrue(cart.getItems().contains("Eggs"));
        verify(shoppingCartRepository).save(cart);
    }

    @Test
    public void testUpdateShoppingCart_RemoveItems() {
        String username = "testUser";
       
        ShoppingCart cart = new ShoppingCart(username, new ArrayList<>(Arrays.asList("Milk", "Eggs")));
        when(shoppingCartRepository.findByUserName(username)).thenReturn(Optional.of(cart));
        String response = zittiService.updateShoppingCart(username, Arrays.asList("Milk"), true);
        assertEquals("Shopping cart updated for user: testUser", response);
        assertFalse(cart.getItems().contains("Milk"));
         verify(shoppingCartRepository).save(cart);
    }
}