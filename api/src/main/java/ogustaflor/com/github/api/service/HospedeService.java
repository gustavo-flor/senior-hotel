package ogustaflor.com.github.api.service;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import ogustaflor.com.github.api.repository.CheckInRepository;
import ogustaflor.com.github.api.repository.HospedeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospedeService {
	
	private final HospedeRepository hospedeRepository;
	private final CheckInRepository checkInRepository;
	
	public Page<Hospede> paginate(int page, int size) {
		Page<Hospede> hospedes = hospedeRepository.findAll(PageRequest.of(page, size));
		
		hospedes.forEach(this::buscaTotaisGastos);
		
		return hospedes;
	}
	
	public boolean existsWithDocumento(String documento) {
		return hospedeRepository.existsHospedeByDocumento(documento);
	}
	
	public Hospede add(Hospede hospede) {
		hospede.setId(null);
		return hospedeRepository.saveAndFlush(hospede);
	}
	
	public Optional<Hospede> findById(Long id) {
		Optional<Hospede> hospede = hospedeRepository.findById(id);
		
		hospede.ifPresent(this::buscaTotaisGastos);
		
		return hospede;
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
	
	public void buscaTotaisGastos(Hospede hospede) {
		List<CheckIn> checkIns = checkInRepository.getAllByHospede_Id(hospede.getId());
		
		Timestamp ultimaDataDeEntrada = null;
		for (CheckIn checkIn : checkIns) {
			hospede.setTotalGastoIntegro(hospede.getTotalGastoIntegro().add(checkIn.getValorHospedagem()));
			if (ultimaDataDeEntrada == null || ultimaDataDeEntrada.before(checkIn.getDataEntrada())) {
				ultimaDataDeEntrada = checkIn.getDataEntrada();
				hospede.setTotalGastoDaUltimaHospedagem(checkIn.getValorHospedagem());
			}
		}
	}
	
}
