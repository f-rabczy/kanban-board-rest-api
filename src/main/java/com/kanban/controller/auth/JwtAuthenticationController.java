package com.kanban.controller.auth;

import com.kanban.model.json.TokenJSON;
import com.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.kanban.service.JwtUserDetailsService;


import com.kanban.config.JwtTokenUtil;
import com.kanban.model.dto.UserDTO;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	private AuthenticationManager authenticationManager;

	private JwtTokenUtil jwtTokenUtil;

	private JwtUserDetailsService userDetailsService;

	private UserService userService;

	@Autowired
	public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO){
		try{
			authenticate(userDTO.getUsername(), userDTO.getPassword());
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(userDTO.getUsername());
			return ResponseEntity.ok(new TokenJSON(jwtTokenUtil.generateToken(userDetails),userService.getIdByUsername(userDTO.getUsername())));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid username or password");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody UserDTO userDTO){
		if(userService.checkIfUserExist(userDTO.getUsername())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with given username already exist");
		}

		return ResponseEntity.ok(userDetailsService.saveDTO(userDTO));
	}

	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new RuntimeException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("INVALID_CREDENTIALS", e);
		}
	}
}

