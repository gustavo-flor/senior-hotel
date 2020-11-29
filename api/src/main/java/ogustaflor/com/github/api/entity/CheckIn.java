package ogustaflor.com.github.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ogustaflor.com.github.api.utils.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class CheckIn {
	
	public static final BigDecimal VALOR_DIARIA_DIAS_DE_SEMANA = new BigDecimal("120.00");
	public static final BigDecimal VALOR_DIARIA_FINS_DE_SEMANA = new BigDecimal("150.00");
	public static final BigDecimal VALOR_ADICIONAL_VEICULO_DIAS_DE_SEMANA = new BigDecimal("15.00");
	public static final BigDecimal VALOR_ADICIONAL_VEICULO_FINS_DE_SEMANA = new BigDecimal("20.00");
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Column
	@NotNull
	@Getter
	@Setter
	private Timestamp dataEntrada;
	
	@Column
	@NotNull
	@Getter
	@Setter
	private Timestamp dataSaida;
	
	@Column
	@NotNull
	@Getter
	@Setter
	private boolean adicionalVeiculo;
	
	@Column
	@Getter
	@Setter
	private BigDecimal valorHospedagem;
	
	@ManyToOne
	@NotNull
	@Getter
	@Setter
	private Hospede hospede;
	
	@JsonIgnore
	@Getter
	@Setter
	private String documentoHospede;
	
	public boolean dataEntradaIsAfterDataSaida() {
		return dataEntrada.after(dataSaida);
	}
	
	public boolean dataEntradaEqualsDataSaida() {
		return dataEntrada.equals(dataSaida);
	}
	
	public boolean dataEntradaEstaNoFuturo() {
		return dataEntrada.after(Timestamp.valueOf(LocalDateTime.now()));
	}
	
	public boolean precisaDeDiariaAdicional() {
		int horaDeSaida = DateUtils.getHoursFromDate(dataSaida);
		int minutoDeSaida = DateUtils.getMinutesFromDate(dataSaida);
		return horaDeSaida > 16 || (horaDeSaida == 16 && minutoDeSaida >= 30);
	}
	
	public int pegaTotalDeDiasDaSemanaHospedado() {
		return DateUtils.getWorkingDaysBetweenTwoDates(dataEntrada, dataSaida);
	}
	
	public int pegaTotalDeDiasNoFinalDaSemanaHospedado() {
		return DateUtils.getNotWorkingDaysBetweenTwoDates(dataEntrada, dataSaida);
	}
	
}

