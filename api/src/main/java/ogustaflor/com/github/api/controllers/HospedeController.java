package ogustaflor.com.github.api.controllers;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.models.Hospede;
import ogustaflor.com.github.api.repositories.HospedeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/hospedes")
@RequiredArgsConstructor
public class HospedeController {
	
	private final HospedeRepository hospedeRepository;
	
	@PostMapping
	public ResponseEntity<Hospede> store(@Valid @RequestBody Hospede hospede) {
		if (hospedeRepository.existsHospedeByDocumento(hospede.getDocumento())) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		hospedeRepository.save(hospede);
		return new ResponseEntity<>(hospede, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public void destroy(@PathVariable(value = "id") long id) {
		hospedeRepository.findById(id).ifPresent(hospedeRepository::delete);
	}
}
