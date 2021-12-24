package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
