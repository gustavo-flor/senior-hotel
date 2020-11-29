package ogustaflor.com.github.api.controller.v1;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.controller.v1.dtos.HospedeRequestBody;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.service.HospedeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/hospedes")
@RequiredArgsConstructor
public class HospedeController extends GenericController {
	
	private final HospedeService hospedeService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<Hospede>> index(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "8", required = false) int size
	) {
		Page<Hospede> hospedes = hospedeService.paginate(page, size);
		return new ResponseEntity<>(hospedes, HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> store(@Valid @RequestBody HospedeRequestBody newHospede) {
		if (hospedeService.existsWithDocumento(newHospede.getDocumento())) {
			return new ResponseEntity<>(criaObjetoComMensagem("Documento já cadastrado."), HttpStatus.CONFLICT);
		}
		
		Hospede hospede = newHospede.toEntity();
		hospedeService.add(hospede);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Hospede> show(@PathVariable(value = "id") Long id) {
		Optional<Hospede> filteredHospede = hospedeService.findById(id);
		if (filteredHospede.isPresent()) {
			Hospede hospede = filteredHospede.get();
			
			return new ResponseEntity<>(hospede, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> update(@RequestBody HospedeRequestBody updatedHospede, @PathVariable(value = "id") Long id) {
		Optional<Hospede> filteredHospede = hospedeService.findById(id);
		if (filteredHospede.isPresent()) {
			Hospede hospede = filteredHospede.get();
			
			boolean hasChanged = false;
			if (updatedHospede.getNome() != null && !hospede.getNome().equals(updatedHospede.getNome())) {
				hospede.setNome(updatedHospede.getNome());
				hasChanged = true;
			}
			if (updatedHospede.getDocumento() != null && !hospede.getDocumento().equals(updatedHospede.getDocumento())) {
				if (hospedeService.existsWithDocumento(hospede.getDocumento())) {
					return new ResponseEntity<>(criaObjetoComMensagem("Documento já cadastrado."), HttpStatus.CONFLICT);
				}
				
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
			
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public void destroy(@PathVariable(value = "id") long id) {
		hospedeService.findById(id).ifPresent(hospedeService::destroy);
	}
	
}
