package ogustaflor.com.github.api.service;

import lombok.RequiredArgsConstructor;
import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.repository.CheckInRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
	
	private final CheckInRepository checkInRepository;
	
	public Page<CheckIn> findAll(int page, int size) {
		return checkInRepository.findAll(PageRequest.of(page, size));
	}
	
	public CheckIn add(CheckIn checkIn) {
		checkIn.setId(null);
		return checkInRepository.saveAndFlush(checkIn);
	}
	
	public boolean existsHospedeInDate(String documento, Timestamp dataEntrada, Timestamp dataSaida) {
		return checkInRepository.existsHospedeCheckedInDatas(documento, dataEntrada, dataSaida);
	}
	
	public Optional<CheckIn> findById(Long id) {
		return checkInRepository.findById(id);
	}
	
}

