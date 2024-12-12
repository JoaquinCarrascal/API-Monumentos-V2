package com.salesianos.triana.api_monumentos_v2.repository;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonumentRepository extends JpaRepository<Monument, Long> {

    @Query("select m from Monument m where m.cityName = :name")
    List<Monument> findByCityName(@Param("name") String cityName);

    @Query("select m from Monument m order by m.monumentName asc")
    List<Monument> findAllByOrderByNameAsc();

    @Query("select m from Monument m order by m.monumentName desc")
    List<Monument> findAllByOrderByNameDesc();

}
