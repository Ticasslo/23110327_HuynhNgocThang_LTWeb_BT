package ngocthang.services.impl;

import ngocthang.dao.IUserDAO;
import ngocthang.dao.impl.UserDAOImpl;
import ngocthang.entity.User;
import ngocthang.services.IUserService;
import ngocthang.utils.Constant;
import ngocthang.utils.RandomUtils;

public class UserServiceImpl implements IUserService {
	IUserDAO userDao = new UserDAOImpl();

	// Login
	@Override
	public User login(String username, String password) {
		User user = this.get(username);
		if (user != null && password != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {
		if (username == null || username.isEmpty())
			return null;
		return userDao.get(username);
	}

	// Register
	@Override
	public boolean register(String email, String password, String username, String fullname, String phone) {
		if (userDao.checkExistUsername(username)) {
			return false;
		}
		
		// Tạo ID ngẫu nhiên cho user mới
		int userId = RandomUtils.generateUserId(100000, 999999);
		
		// Kiểm tra ID đã tồn tại chưa, nếu có thì tạo lại
		while (userDao.checkExistId(userId)) {
			userId = RandomUtils.generateUserId(100000, 999999);
		}
		
		long millis = System.currentTimeMillis();
		java.sql.Timestamp created = new java.sql.Timestamp(millis);
		userDao.insert(new User(userId, email, username, fullname, password, null, Constant.ROLE_USER, phone, created));
		return true;
	}

	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return userDao.checkExistPhone(phone);
	}

	@Override
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public User findByUsernameOrEmail(String identifier) {
		// Tìm theo username trước
		User user = userDao.findByUserName(identifier);

		// Nếu không tìm thấy, tìm theo email
		if (user == null) {
			user = userDao.findByEmail(identifier);
		}

		return user;
	}

	@Override
	public boolean updatePassword(String username, String newPassword) {
		return userDao.updatePassword(username, newPassword);
	}
}
