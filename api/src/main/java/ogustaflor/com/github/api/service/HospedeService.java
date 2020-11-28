package ogustaflor.com.github.api.service;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.repository.HospedeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospedeService {
	
	private final HospedeRepository hospedeRepository;
	
	public Page<Hospede> findAll(int page, int size) {
		return hospedeRepository.findAll(PageRequest.of(page, size));
	}
	
	public boolean existsWithDocumento(String documento) {
		return hospedeRepository.existsHospedeByDocumento(documento);
	}
	
	public Hospede add(Hospede hospede) {
		hospede.setId(null);
		return hospedeRepository.saveAndFlush(hospede);
	}
	
	public Optional<Hospede> findById(Long id) {
		return hospedeRepository.findById(id);
	}
	
	public void destroy(Hospede hospede) {
		hospedeRepository.delete(hospede);
	}
	
	public void update(Hospede hospede) {
		hospedeRepository.save(hospede);
	}
	
	public Optional<Hospede> findByDocumento(String documento) {
		return hospedeRepository.findByDocumento(documento);
	}
	
}
