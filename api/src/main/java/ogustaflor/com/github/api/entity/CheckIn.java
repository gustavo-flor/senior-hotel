package ogustaflor.com.github.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class CheckIn {
	
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
	private Boolean adicionalVeiculo;
	
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
	
}

