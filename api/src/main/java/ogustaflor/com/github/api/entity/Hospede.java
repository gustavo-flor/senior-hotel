package ogustaflor.com.github.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Hospede {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Column
	@NotEmpty
	@Getter
	@Setter
	private String nome;
	
	@Column(unique = true)
	@NotEmpty
	@Getter
	@Setter
	private String documento;
	
	@Column
	@NotEmpty
	@Getter
	@Setter
	private String telefone;
	
}
