package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
