package com.vides.domi_backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vides.domi_backend.model.User;
import com.vides.domi_backend.repository.UserRepository;

@SpringBootTest
class DomiBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void crearUsuarioYBuscarlo() {
		User user = User.builder()
				.nombre("Juan")
				.email("juan@test.com")
				.password("1234")
				.rol(User.Rol.USER)
				.build();
		userRepository.save(user);
		User encontrado = userRepository.findByEmail("juan@test.com").orElse(null);
		Assertions.assertNotNull(encontrado);
		Assertions.assertEquals("Juan", encontrado.getNombre());
	}

}
