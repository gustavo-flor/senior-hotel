package ogustaflor.com.github.api.repository;

import ogustaflor.com.github.api.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
	
	@Query(
		"SELECT CASE WHEN COUNT(ci.id) > 0 THEN true ELSE false END " +
		"FROM CheckIn ci " +
		"JOIN Hospede h ON h.id = ci.hospede.id " +
		"WHERE h.documento = ?1 " +
		"AND (" +
		"	(?2 BETWEEN ci.dataEntrada AND ci.dataSaida) " +
		"	OR " +
		"	(?3 BETWEEN ci.dataEntrada AND ci.dataSaida)" +
		")"
	)
	boolean existsHospedeCheckedInDatas(String documento, Timestamp dataEntrada, Timestamp dataSaida);
	
}
