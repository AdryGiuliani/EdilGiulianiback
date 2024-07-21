package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.BookingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RepoBookingDay extends JpaRepository<BookingDay, Long> {

    @Query("SELECT bd FROM BookingDay bd " +
            "JOIN FETCH bd.subp sp JOIN FETCH sp.mezzo m " +
            "WHERE m.id = :idMezzo AND bd.giorno = :day")
    List<BookingDay> findAllByIdMezzoAndDay(@Param("idMezzo") Long idMezzo, @Param("day") Date day);
}
