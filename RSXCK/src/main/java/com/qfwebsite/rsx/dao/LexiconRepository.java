package com.qfwebsite.rsx.dao;

import com.qfwebsite.rsx.bean.Lexicon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
@Transactional(readOnly = true)
public interface LexiconRepository extends JpaRepository<Lexicon, Long> {
    @Transactional
    @Modifying
    @Query(value = "update lexicon set state = ?1, link = ?2, update_time = ?3 where id = ?4", nativeQuery = true)
    void updateState(Integer state, String link, Date updateTime, Long id);

    List<Lexicon> findByBrandNameHand(String brandNameHand);

    List<Lexicon> findByNameId(Integer nameId);

    List<Lexicon> findByNameIdAndState(Integer nameId,Integer state);

    List<Lexicon> findByBrandNameHandAndNameId(String brandNameHand, Integer nameId);

    List<Lexicon> findByStateAndRegistrationStatusAndGeneral(Integer state, String registrationStatus, Integer general);

    List<Lexicon> findByGeneral(Integer general);
}
