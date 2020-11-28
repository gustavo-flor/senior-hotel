package ogustaflor.com.github.api.controller.v1.dtos;

import lombok.Getter;
import lombok.Setter;
import ogustaflor.com.github.api.entity.Hospede;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HospedeRequestBody {
	
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String nome;
	
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String documento;
	
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String telefone;
	
	public Hospede toEntity() {
		Hospede hospede = new Hospede();
		hospede.setNome(nome);
		hospede.setDocumento(documento);
		hospede.setTelefone(telefone);
		return hospede;
	}
}
