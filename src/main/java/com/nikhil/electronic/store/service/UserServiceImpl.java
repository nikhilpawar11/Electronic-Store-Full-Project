package com.nikhil.electronic.store.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nikhil.electronic.store.customresponses.PegiableResponse;
import com.nikhil.electronic.store.dto.UserDto;
import com.nikhil.electronic.store.entity.Role;
import com.nikhil.electronic.store.entity.User;
import com.nikhil.electronic.store.exception.ResourceNotFoundException;
import com.nikhil.electronic.store.helper.Helper;
import com.nikhil.electronic.store.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	

	@Override
	public UserDto createUser(UserDto userDto) {
		
		// convert UserDto(DTO) into user(entity) using ModelMapper
		User user = mapper.map(userDto, User.class);
		// set random user id
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		// set encoded password to user
		String encodedPassword = encoder.encode(userDto.getPassword());
		user.setPassword(encodedPassword);
		// set default user role as user
		user.setRole(Role.ROLE_USER);
		
		//save user
		User saveUser = userRepo.save(user);
		
		// convert user (entity) into UserDto(DTO) using ModelMapper
		return mapper.map(saveUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		
		// find user by id
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usernot found with given id "+userId));
		
		// update the info of user
		userById.setName(userDto.getName());
		userById.setEmail(userDto.getEmail());
		userById.setPassword(encoder.encode(userDto.getPassword()));
		userById.setGender(userDto.getGender());
		userById.setImageName(userDto.getImageName());
		userById.setRole(userDto.getRole());
		
		// save updated user
		User updateUser = userRepo.save(userById);
		
		// convert userById (entity) into UserDto(DTO) using ModelMapper
		return mapper.map(updateUser, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) throws IOException {

		// find user by id
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usernot found with given id " + userId));

		// delete user profile image
		String fullPath = imagePath + userById.getImageName();

		try {

			Path path = Paths.get(fullPath);
			Files.delete(path);

		} catch (NoSuchFileException ex) {
			ex.printStackTrace();
		}

		// delete user
		userRepo.delete(userById);

	}

	@Override
	public UserDto getUserById(String userId) {
		
		// find user by id
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usernot found with given id "+userId));
		
		// convert userById (entity) into UserDto(DTO) using ModelMapper
		return mapper.map(userById, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		// get all users
		List<User> allUser = userRepo.findAll();
		
		// convert allUser (entity) into allUserDto(DTO) using ModelMapper and java 8 features
		List<UserDto> allUserDto = allUser.stream().map(ex -> mapper.map(ex, UserDto.class)).collect(Collectors.toList());
		
		return allUserDto;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		
		// find user by email
		User userByEmail = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email "+email));
		
		// convert userByEmail(entity) into UserDto(DTO) using ModelMapper
		return mapper.map(userByEmail, UserDto.class);
	}

	@Override
	public PegiableResponse<UserDto> getUserWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<User> page = userRepo.findAll(pageable);
		
		PegiableResponse<UserDto> peginationResponse = Helper.getPeginationResponse(page, UserDto.class);
		
		return peginationResponse;
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		
		List<User> user = userRepo.findByNameContaining(keyword);
		
		List<UserDto> userList = user.stream().map(ex -> mapper.map(ex, UserDto.class)).collect(Collectors.toList());
		
		return userList;
	}

}

