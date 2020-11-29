package ogustaflor.com.github.api.repository;

import ogustaflor.com.github.api.entity.CheckIn;
import ogustaflor.com.github.api.entity.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
	
	List<CheckIn> getAllByHospede_Id(Long hospedeId);
	
	@Query(
		"SELECT CASE WHEN COUNT(ci.id) > 0 THEN true ELSE false END " +
		"FROM CheckIn ci " +
		"JOIN Hospede h ON h.id = ci.hospede.id " +
		"WHERE h.documento = ?1 " +
		"AND (" +
		"	(?2 BETWEEN ci.dataEntrada AND ci.dataSaida) " +
		"	OR " +
		"	(?3 BETWEEN ci.dataEntrada AND ci.dataSaida) " +
		") "
	)
	boolean existsHospedeCheckedInDatas(String documento, Timestamp dataEntrada, Timestamp dataSaida);
	
	@Query(
		"SELECT h " +
		"FROM Hospede h " +
		"JOIN CheckIn ci ON ci.hospede.id = h.id " +
		"WHERE ?1 BETWEEN ci.dataEntrada AND ci.dataSaida " +
		"GROUP BY h.id "
	)
	List<Hospede> filterIn(Timestamp dataAtual);
	
	@Query(
		"SELECT h " +
		"FROM Hospede h " +
		"JOIN CheckIn ci ON ci.hospede.id = h.id " +
		"WHERE ?1 BETWEEN ci.dataEntrada AND ci.dataSaida " +
		"AND ( " +
		"	h.nome LIKE %?2% " +
		"	OR " +
		"	h.documento LIKE %?2% " +
		"	OR " +
		"	h.telefone LIKE %?2% " +
		") " +
		"GROUP BY h.id "
	)
	List<Hospede> filterInWithContent(Timestamp dataAtual, String content);
	
	@Query(
		"SELECT h " +
		"FROM Hospede h " +
		"JOIN CheckIn ci ON ci.hospede.id = h.id " +
		"WHERE h.id NOT IN ( " +
		"	SELECT ci.hospede.id " +
		"	FROM CheckIn ci " +
		"	WHERE ?1 BETWEEN ci.dataEntrada AND ci.dataSaida " +
		") " +
		"GROUP BY h.id "
	)
	List<Hospede> filterOut(Timestamp dataAtual);
	
	@Query(
		"SELECT h " +
		"FROM Hospede h " +
		"JOIN CheckIn ci ON ci.hospede.id = h.id " +
		"WHERE h.id NOT IN ( " +
		"	SELECT ci.hospede.id " +
		"	FROM CheckIn ci " +
		"	WHERE ?1 BETWEEN ci.dataEntrada AND ci.dataSaida " +
		") AND ( " +
		"	h.nome LIKE %?2% " +
		"	OR " +
		"	h.documento LIKE %?2% " +
		"	OR " +
		"	h.telefone LIKE %?2% " +
		") " +
		"GROUP BY h.id "
	)
	List<Hospede> filterOutWithContent(Timestamp dataAtual, String content);
	
}
