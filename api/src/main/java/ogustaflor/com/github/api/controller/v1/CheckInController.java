package ogustaflor.com.github.api.controller.v1;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.controller.v1.dtos.CheckInRequestBody;
import ogustaflor.com.github.api.controller.v1.dtos.CheckInResponseBody;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.service.CheckInService;
import ogustaflor.com.github.api.service.HospedeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/check-ins")
@RequiredArgsConstructor
public class CheckInController {
	
	private final CheckInService checkInService;
	private final HospedeService hospedeService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<CheckInResponseBody>> index(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "8", required = false) int size
	) {
		Page<CheckIn> checkIns = checkInService.findAll(page, size);
		return new ResponseEntity<>(checkIns.map(CheckInResponseBody::transform), HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> store(@Valid @RequestBody CheckInRequestBody newCheckIn) throws ParseException {
		CheckIn checkIn = newCheckIn.toEntity();
		if (checkIn.dataEntradaIsAfterDataSaida() || checkIn.dataEntradaEqualsDataSaida()) {
			Map<String, Object> body = new HashMap<>();
			body.put("message", "Data de Entrada deve ser menor que Data de Saída.");
			return new ResponseEntity<>(body, HttpStatus.CONFLICT);
		}
		String documento = checkIn.getDocumentoHospede();
		Optional<Hospede> filteredHospede = hospedeService.findByDocumento(documento);
		if (filteredHospede.isEmpty()) {
			Map<String, Object> body = new HashMap<>();
			body.put("message", "Documento do hóspede informado não existe.");
			return new ResponseEntity<>(body, HttpStatus.CONFLICT);
		}
		checkIn.setHospede(filteredHospede.get());
		if (checkInService.existsHospedeInDate(documento, checkIn.getDataEntrada(), checkIn.getDataSaida())) {
			Map<String, Object> body = new HashMap<>();
			body.put("message", "Hóspede já fez check in no período.");
			return new ResponseEntity<>(body, HttpStatus.CONFLICT);
		}
		checkInService.add(checkIn);
		return new ResponseEntity<>(CheckInResponseBody.transform(checkIn), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<CheckInResponseBody> show(@PathVariable(value = "id") Long id) {
		Optional<CheckIn> filteredCheckIn = checkInService.findById(id);
		if (filteredCheckIn.isPresent()) {
			CheckIn checkIn = filteredCheckIn.get();
			
			return new ResponseEntity<>(CheckInResponseBody.transform(checkIn), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
