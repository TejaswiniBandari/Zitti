package com.example.demo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.Entity.ShoppingCart;
import com.example.demo.Request.CommandRequest;
import com.example.demo.Request.ShoppingCartUpdateRequest;
import com.example.demo.controller.ZittiController;
import com.example.demo.service.ZittiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ZittiController.class)
public class ZittiControllerTest {
	

	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private ZittiService zittiService;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Test
	    public void testHandleCommand() throws Exception {
	        CommandRequest request = new CommandRequest("testUser", "Hey. How are you?");
	        when(zittiService.processCommand("testUser", "Hey. How are you?")).thenReturn("Hello, I am doing great.");
	        
	        mockMvc.perform(post("/zitti/command")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk())
	                .andExpect((ResultMatcher) content().string("Hello, I am doing great."));
	    }

	    @Test
	    public void testGetShoppingCart() throws Exception {
	        ShoppingCart shoppingCart = new ShoppingCart(1, "testUser", new ArrayList<>());
	        when(zittiService.getShoppingCart("testUser")).thenReturn(Optional.of(shoppingCart));
	        
	        mockMvc.perform(get("/zitti/testUser"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.username", is("testUser")));
	    }

	    @Test
	    public void testGetShoppingCartNotFound() throws Exception {
	        when(zittiService.getShoppingCart("unknownUser")).thenReturn(Optional.empty());
	        
	        mockMvc.perform(get("/zitti/unknownUser"))
	                .andExpect(status().isNotFound());
	    }
	    
	    @Test
	    public void testDeleteShoppingCart() throws Exception {
	        doNothing().when(zittiService).deleteShoppingCart("testUser");
	        
	        mockMvc.perform(delete("/zitti/testUser"))
	                .andExpect(status().isOk())
	                .andExpect((ResultMatcher) content().string("Shopping cart deleted for user: testUser"));
	    }
	   
	    
	    @Test
	    public void testUpdateShoppingCart() throws Exception {
	        ShoppingCartUpdateRequest updateRequest = new ShoppingCartUpdateRequest(Arrays.asList("item1", "item2"), false);
	        when(zittiService.updateShoppingCart("testUser", updateRequest.getItems(), false)).thenReturn("Cart updated");
	        
	        mockMvc.perform(put("/zitti/testUser")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(updateRequest)))
	                .andExpect(status().isOk())
	                .andExpect((ResultMatcher) content().string("Cart updated"));
	    }
	}
