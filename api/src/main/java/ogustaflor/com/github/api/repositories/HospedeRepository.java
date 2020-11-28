package ogustaflor.com.github.api.repositories;

import ogustaflor.com.github.api.models.Hospede;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
	
	boolean existsHospedeByDocumento(String documento);
	
}
