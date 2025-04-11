package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.ShoppingCart;
import com.example.demo.Request.CommandRequest;
import com.example.demo.Request.ShoppingCartUpdateRequest;
import com.example.demo.service.ZittiService;

import jakarta.validation.Valid;

@RequestMapping("/zitti/")
@RestController
public class ZittiController {
	
	@Autowired
    ZittiService zittiService;

	@PostMapping("/command")
	public ResponseEntity<String> handleCommand(@Valid @RequestBody CommandRequest request) {
	    String response = zittiService.processCommand(request.getUsername(), request.getCommand());
	    return ResponseEntity.ok(response);
	}
	
	// GET mapping to fetch a user's shopping cart
    @GetMapping("/{username}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable String username) {
        Optional<ShoppingCart> shoppingCart = zittiService.getShoppingCart(username);
        return shoppingCart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 // DELETE mapping to delete a user's entire shopping cart
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteShoppingCart(@PathVariable String username) {
        zittiService.deleteShoppingCart(username);
        return ResponseEntity.ok("Shopping cart deleted for user: " + username);
    }
    
 
    
    @PutMapping("/{username}")
    public ResponseEntity<String> updateShoppingCart(@PathVariable String username, @RequestBody ShoppingCartUpdateRequest updateRequest) {
        String response = zittiService.updateShoppingCart(username, updateRequest.getItems(), updateRequest.isRemove());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/printcommand")
    pubilc ResponseEntity<String> printCommand(){
        String response = "Hello I'm Tejaswini, good to know u";
        reuturn new responseEntity.ok(response);
    }

	 @GetMapping("/printcommand123")
    pubilc ResponseEntity<String> printCommand(){
        String response = "I'm good";
        reuturn new responseEntity.ok(response);
    }

    @GetMapping("/printcommand456")
    pubilc ResponseEntity<String> printCommand(){
        String response = "What are you doing";
        reuturn new responseEntity.ok(response);
    }

	
    @GetMapping("/printcommand456")
    pubilc ResponseEntity<String> printCommand(){
        String response = "I'm cooking";
        reuturn new responseEntity.ok(response);
    }


}
