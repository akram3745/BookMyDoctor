package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.FeedBack;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {

}
