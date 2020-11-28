package ogustaflor.com.github.api.controller;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.service.HospedeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hospedes")
@RequiredArgsConstructor
public class HospedeController {
	
	private final HospedeService hospedeService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<Hospede>> index(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "8", required = false) int size
	) {
		Page<Hospede> hospedes = hospedeService.findAll(page, size);
		return new ResponseEntity<>(hospedes, HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> store(@Valid @RequestBody Hospede hospede) {
		if (hospedeService.existsWithDocumento(hospede.getDocumento())) {
			Map<String, Object> body = new HashMap<>();
			body.put("message", "Documento já cadastrado.");
			return new ResponseEntity<>(body, HttpStatus.CONFLICT);
		}
		
		hospedeService.add(hospede);
		return new ResponseEntity<>(hospede, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> show(@PathVariable(value = "id") Long id) {
		Optional<Hospede> filteredHospede = hospedeService.findById(id);
		if (filteredHospede.isPresent()) {
			Hospede hospede = filteredHospede.get();
			
			return new ResponseEntity<>(hospede, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> update(@RequestBody Hospede updatedHospede, @PathVariable(value = "id") Long id) {
		Optional<Hospede> filteredHospede = hospedeService.findById(id);
		if (filteredHospede.isPresent()) {
			Hospede hospede = filteredHospede.get();
			boolean hasChanged = false;
			if (updatedHospede.getNome() != null && !hospede.getNome().equals(updatedHospede.getNome())) {
				hospede.setNome(updatedHospede.getNome());
				hasChanged = true;
			}
			if (updatedHospede.getDocumento() != null && !hospede.getDocumento().equals(updatedHospede.getDocumento())) {
				hospede.setDocumento(updatedHospede.getDocumento());
				hasChanged = true;
			}
			if (updatedHospede.getTelefone() != null && !hospede.getTelefone().equals(updatedHospede.getTelefone())) {
				hospede.setTelefone(updatedHospede.getTelefone());
				hasChanged = true;
			}
			
			if (hasChanged) {
				hospedeService.update(hospede);
			}
			
			return new ResponseEntity<>(hospede, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public void destroy(@PathVariable(value = "id") long id) {
		hospedeService.findById(id).ifPresent(hospedeService::destroy);
	}
}
