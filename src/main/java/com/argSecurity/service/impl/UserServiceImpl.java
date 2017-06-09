/**
 * @author daniela.depablos
 * Benetech trainning app Copyrights reserved
 * Service implementation for the User entity. Use the UserRepository to give
 * standard service about the Skill entity. <br/>
 * "@Service" trigger the Spring bean nature.<br/>
 * "@Transactionnal" trigger the transactionnal nature of all bean methods.<br/>
 * Implements not only the application's UserService but also:<br/>
 * UserDetailsService: to use the service as Spring Security authentication
 * provider.<br/>
 * InitializingBean: to have a callback after Spring loading in order to insert
 * the first user in db if there is none.
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see org.springframework.beans.factory.InitializingBean
 */
package com.argSecurity.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.argSecurity.model.User;
import com.argSecurity.repository.UserRepository;


@Service
// @Transactional
public class UserServiceImpl implements UserDetailsService, InitializingBean {

	/**
	 * Autowiring the repository implementation needed to do the job
	 */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * This is the main (and only) method to implement to be a Spring Security
	 * authentication provider. It needs only to return the user corresponding
	 * to the login in parameter or launch an exception if not exist. The
	 * password checking is fully managed by handling the UserDetails returned.<br/>
	 * As the password checking is not our work, the password encoding can be
	 * configured in Spring Security. It's not done yet but it can be an
	 * evolution of this tutorial.
	 */
	public UserDetails loadUserByUsername(String username)
			throws AuthenticationException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			
			throw new UsernameNotFoundException(username + " n'existe pas");
		}else {
			user.setLogin((user.getLogin().toLowerCase()));
			return user;
		}
	}
	
	
	

	/**
	 * By implementing InitializingBean in a Spring bean, this method will be
	 * launch after Spring wirings are finished.<br/>
	 * It's used here to perform a check at the loading of the application on
	 * the content of the user table a adding the first user if it's empty. This
	 * way, there is no need of SQL initialization script which is so boring to
	 * handle (and even more on CloudFoundry)
	 */
	public void afterPropertiesSet() throws Exception {

	}
	
	/**
	 * 
	 * @param email
	 * @return
	 * @throws AuthenticationException
	 */
	public User loadUserByEmail(String email) throws AuthenticationException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email + " not linked to any user");
		} else {
			return user;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws AuthenticationException
	 */
	public User findOne(int id) throws AuthenticationException {
		User user = userRepository.findOne(id);
		if (user == null) {
			throw new UsernameNotFoundException(id + " user id does not exist");
		} else {
			return user;
		}
	}
		
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findById(int userId){
		
		return userRepository.findOne(userId);
	}
	
}
