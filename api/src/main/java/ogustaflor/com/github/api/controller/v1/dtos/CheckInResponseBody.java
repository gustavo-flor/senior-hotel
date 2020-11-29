package ogustaflor.com.github.api.controller.v1.dtos;

import lombok.Getter;
import lombok.Setter;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.utils.DateUtils;

public class CheckInResponseBody {
	
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String dataEntrada, dataSaida;
	
	@Getter
	@Setter
	private Boolean adicionalVeiculo;
	
	@Getter
	@Setter
	private Hospede hospede;
	
	public static CheckInResponseBody transform(CheckIn checkIn) {
		CheckInResponseBody checkInResponseBody = new CheckInResponseBody();
		checkInResponseBody.setId(checkIn.getId());
		checkInResponseBody.setDataEntrada(DateUtils.toString(checkIn.getDataEntrada()));
		checkInResponseBody.setDataSaida(DateUtils.toString(checkIn.getDataSaida()));
		checkInResponseBody.setHospede(checkIn.getHospede());
		checkInResponseBody.setAdicionalVeiculo(checkIn.isAdicionalVeiculo());
		return checkInResponseBody;
	}
	
}
