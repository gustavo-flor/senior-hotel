package ogustaflor.com.github.api.repository;

import ogustaflor.com.github.api.entity.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
	
	boolean existsHospedeByDocumento(String documento);
	
	Optional<Hospede> findByDocumento(String documento);
	
}
