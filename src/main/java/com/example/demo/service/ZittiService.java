package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ShoppingCart;
import com.example.demo.Exception.InvalidCommandException;
import com.example.demo.respository.ShoppingCartRepository;

@Service
public class ZittiService {
@Autowired	
 ShoppingCartRepository shoppingCartRepository;

public int generateUniqueId() {
    // Find the shopping cart with the highest ID and increment it by 1
    Optional<ShoppingCart> lastCart = shoppingCartRepository.findTopByOrderByIdDesc();
    return lastCart.map(cart -> cart.getId() + 1).orElse(1); // Start with id=1 if no records exist
}

public String processCommand(String username, String command) {
		 if (command.equalsIgnoreCase("Hey. How are you?")) {
				        return "Hello, I am doing great.";
				    } else if (command.equalsIgnoreCase("How's the weather outside?")) {
				        return getRandomWeatherResponse();
				    } else if (command.equalsIgnoreCase("Clean my room.")) {
				        return "Room is cleaned. It looks tidy now. Job completed at " + LocalDateTime.now();
				    } else if (command.startsWith("Add ")) {
				        return addToShoppingCart(username, command);
				    } else if (command.equalsIgnoreCase("Read my shopping list.")) {
				        return readShoppingList(username);
				    } else {
				        throw new InvalidCommandException( "Hmm. I don't know that:" +command);// "Add Laptop to my shopping list."
				    }
				}

public String addToShoppingCart(String username, String input) {
    String item = input.substring(4, input.indexOf(" to my shopping list."));
    
    // Generate a unique ID for new shopping cart
    ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(username)
        .orElse(new ShoppingCart(generateUniqueId(), username, new ArrayList<>()));
    
    // Ensure items list is initialized
    if (shoppingCart.getItems() == null) {
        shoppingCart.setItems(new ArrayList<>());
    }

    // Add the item to the shopping cart
    shoppingCart.getItems().add(item);
    
    // Save the updated shopping cart
    shoppingCartRepository.save(shoppingCart);

    return item + " added to your shopping list";
}



public String readShoppingList(String username) {
    ShoppingCart shoppingList = shoppingCartRepository.findByUserName(username)
        .orElse(new ShoppingCart(username, new ArrayList<>()));
    
    // Ensure the items list is initialized
    if (shoppingList.getItems() == null || shoppingList.getItems().isEmpty()) {
        return "Your shopping list is empty.";
    }
    
    return "Here is your shopping list: " + String.join(", ", shoppingList.getItems());
}

			
				private String getRandomWeatherResponse() {
			        String[] responses = {
			            "It's pleasant outside. You should take a walk.",
			            "It's raining outside. Remember to take an umbrella.",
			            "It's snowing outside. Feels Christmas-y!"
			        };
			        return responses[new Random().nextInt(responses.length)];
			    }

			   
			    public Optional<ShoppingCart> getShoppingCart(String username) {
			        return shoppingCartRepository.findByUserName(username);
			    }
			 // DELETE: Delete the entire shopping cart
			    public void deleteShoppingCart(String username) {
			        shoppingCartRepository.findByUserName(username)
			            .ifPresent(shoppingCartRepository::delete);
			    }
			    
			
			    
			 // PUT: Update the shopping cart (add or remove items)
			    public String updateShoppingCart(String username, List<String> items, boolean remove) {
			        ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(username)
			            .orElse(new ShoppingCart(generateUniqueId(), username, new ArrayList<>()));

			        if (remove) {
			            shoppingCart.getItems().removeAll(items);
			        } else {
			            shoppingCart.getItems().addAll(items);
			        }

			        shoppingCartRepository.save(shoppingCart);
			        return "Shopping cart updated for user: " + username;
			    }
				
}
			


