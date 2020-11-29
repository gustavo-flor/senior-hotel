package ogustaflor.com.github.api.controller.v1;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.controller.v1.dtos.CheckInRequestBody;
import ogustaflor.com.github.api.controller.v1.dtos.CheckInResponseBody;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.service.CheckInService;
import ogustaflor.com.github.api.service.HospedeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/v1/check-ins")
@RequiredArgsConstructor
public class CheckInController extends GenericController {
	
	private final CheckInService checkInService;
	private final HospedeService hospedeService;
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> store(@Valid @RequestBody CheckInRequestBody newCheckIn) throws ParseException {
		CheckIn checkIn = newCheckIn.toEntity();
		if (checkIn.dataEntradaEstaNoFuturo()) {
			return new ResponseEntity<>(criaObjetoComMensagem("Data de Entrada não pode estar no futuro."), HttpStatus.CONFLICT);
		}
		if (checkIn.dataEntradaIsAfterDataSaida() || checkIn.dataEntradaEqualsDataSaida()) {
			return new ResponseEntity<>(criaObjetoComMensagem("Data de Entrada deve ser menor que Data de Saída."), HttpStatus.CONFLICT);
		}
		String documento = checkIn.getDocumentoHospede();
		Optional<Hospede> filteredHospede = hospedeService.findByDocumento(documento);
		if (filteredHospede.isEmpty()) {
			return new ResponseEntity<>(criaObjetoComMensagem("Documento do hóspede informado não existe."), HttpStatus.CONFLICT);
		}
		checkIn.setHospede(filteredHospede.get());
		if (checkInService.existsHospedeInDate(documento, checkIn.getDataEntrada(), checkIn.getDataSaida())) {
			return new ResponseEntity<>(criaObjetoComMensagem("Hóspede já fez check in no período."), HttpStatus.CONFLICT);
		}
		checkInService.add(checkIn);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/hospedes", produces = "application/json")
	public ResponseEntity<?> find(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "8", required = false) int size,
		@RequestParam(name = "status", defaultValue = "IN") String status,
		@RequestParam(name = "content", defaultValue = "") String content
	) {
		List<String> allowedsStatus = Arrays.asList("IN", "OUT");
		if (allowedsStatus.stream().anyMatch(allowedStatus -> allowedStatus.equals(status.toUpperCase()))) {
			Page<Hospede> hospedes = checkInService.filterHospedes(status, content, PageRequest.of(page, size));
			return new ResponseEntity<>(hospedes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(criaObjetoComMensagem("Status informado é inválido."), HttpStatus.CONFLICT);
	}
	
}
