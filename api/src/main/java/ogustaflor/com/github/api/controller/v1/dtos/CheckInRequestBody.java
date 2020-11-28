package ogustaflor.com.github.api.controller.v1.dtos;

import lombok.Getter;
import lombok.Setter;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.utils.DateUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

public class CheckInRequestBody {
	
	@Getter
	@Setter
	@NotEmpty
	@NotNull
	private String dataEntrada;
	
	@Getter
	@Setter
	@NotEmpty
	@NotNull
	private String dataSaida;
	
	@Getter
	@Setter
	@NotEmpty
	@NotNull
	private String documentoHospede;
	
	@Getter
	@Setter
	@NotNull
	private Boolean adicionalVeiculo;
	
	public CheckIn toEntity() throws ParseException {
		CheckIn checkIn = new CheckIn();
		checkIn.setDataEntrada(DateUtils.toTimestamp(dataEntrada));
		checkIn.setDataSaida(DateUtils.toTimestamp(dataSaida));
		checkIn.setDocumentoHospede(documentoHospede);
		Hospede hospede = new Hospede();
		hospede.setDocumento(documentoHospede);
		checkIn.setHospede(hospede);
		checkIn.setAdicionalVeiculo(adicionalVeiculo);
		return checkIn;
	}
	
}
