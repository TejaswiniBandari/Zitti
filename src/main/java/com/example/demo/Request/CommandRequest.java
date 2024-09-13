package com.example.demo.Request;

import jakarta.validation.constraints.NotNull;

public class CommandRequest {
	@NotNull(message = "Username is required")
    private String username;
    
    @NotNull(message = "Command is required")
    private String command;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CommandRequest(@NotNull(message = "Username is required") String username,
			@NotNull(message = "Command is required") String command) {
		super();
		this.username = username;
		this.command = command;
	}

   

}
