package com.epam.company.repository;

import com.epam.company.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository  extends JpaRepository<User, Long> {

	User findById(Long id);

	Optional<User> findOneBySsoId(String sso);
	
	void removeBySsoId(String sso);

	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByPhone(String phone);

	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

	Optional<User> removeByEmail(String email);
	Optional<User> removeByPhone(String phone);
}

