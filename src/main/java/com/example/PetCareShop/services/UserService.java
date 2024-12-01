package com.example.PetCareShop.services;

import com.example.PetCareShop.models.User;

public interface UserService {
	User findByUserName(String userName);
}
