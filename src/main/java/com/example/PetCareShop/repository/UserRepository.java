package com.example.PetCareShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PetCareShop.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	//Tra ve 1 ten nguoi dung la duy nhat
	User findByUserName(String userName);
}
