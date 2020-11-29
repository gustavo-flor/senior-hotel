package ogustaflor.com.github.api.service;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.repository.CheckInRepository;
import ogustaflor.com.github.api.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInService {
	
	private final CheckInRepository checkInRepository;
	private final HospedeService hospedeService;
	
	public List<Hospede> filterHospedes(String status, String content) {
		List<Hospede> hospedes = Collections.emptyList();
		switch (status.toUpperCase()) {
			case "IN":
				hospedes = content.isEmpty()
					? checkInRepository.filterIn(Timestamp.valueOf(LocalDateTime.now()))
					: checkInRepository.filterInWithContent(Timestamp.valueOf(LocalDateTime.now()), content);
				break;
			case "OUT":
				hospedes = content.isEmpty()
					? checkInRepository.filterOut(Timestamp.valueOf(LocalDateTime.now()))
					: checkInRepository.filterOutWithContent(Timestamp.valueOf(LocalDateTime.now()), content);
				break;
		}
		
		hospedes.forEach(hospedeService::buscaTotaisGastos);
		
		return hospedes;
	}
	
	public CheckIn add(CheckIn checkIn) {
		checkIn.setId(null);
		calculaHospedagem(checkIn);
		return checkInRepository.saveAndFlush(checkIn);
	}
	
	public boolean existsHospedeInDate(String documento, Timestamp dataEntrada, Timestamp dataSaida) {
		return checkInRepository.existsHospedeCheckedInDatas(documento, dataEntrada, dataSaida);
	}
	
	public void calculaHospedagem(CheckIn checkIn) {
		int diasDeSemana = checkIn.pegaTotalDeDiasDaSemanaHospedado();
		int diasNoFimDeSemana = checkIn.pegaTotalDeDiasNoFinalDaSemanaHospedado();
		
		BigDecimal valorDaHospedagem = BigDecimal.ZERO
				.add(CheckIn.VALOR_DIARIA_DIAS_DE_SEMANA.multiply(BigDecimal.valueOf(diasDeSemana)))
				.add(CheckIn.VALOR_DIARIA_FINS_DE_SEMANA.multiply(BigDecimal.valueOf(diasNoFimDeSemana)));
		
		if (checkIn.isAdicionalVeiculo()) {
			valorDaHospedagem = valorDaHospedagem
					.add(CheckIn.VALOR_ADICIONAL_VEICULO_DIAS_DE_SEMANA.multiply(BigDecimal.valueOf(diasDeSemana)))
					.add(CheckIn.VALOR_ADICIONAL_VEICULO_FINS_DE_SEMANA.multiply(BigDecimal.valueOf(diasNoFimDeSemana)));
		}
		
		if (checkIn.precisaDeDiariaAdicional()) {
			BigDecimal diariaAdicional = DateUtils.isOnWeekends(checkIn.getDataSaida())
					? CheckIn.VALOR_ADICIONAL_VEICULO_FINS_DE_SEMANA
					: CheckIn.VALOR_ADICIONAL_VEICULO_DIAS_DE_SEMANA;
			valorDaHospedagem = valorDaHospedagem.add(diariaAdicional);
		}
		
		checkIn.setValorHospedagem(valorDaHospedagem);
	}
	
}

