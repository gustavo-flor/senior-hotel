package ogustaflor.com.github.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Hospede {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Column
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String nome;
	
	@Column(unique = true)
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String documento;
	
	@Column
	@NotEmpty
	@NotNull
	@Getter
	@Setter
	private String telefone;
	
}
