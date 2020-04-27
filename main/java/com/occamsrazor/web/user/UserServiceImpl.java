package com.occamsrazor.web.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import java.util.Set;



import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


	private Map<String, Object> map;
	public final static String FILE_PATH = "C:\\Users\\bit\\spring-workspace\\occamsrazor\\src\\main\\resources\\static\\member\\";
	public UserServiceImpl() {
		map = new HashMap<>();
	}

	@Override
	public void add(User user) {
		map.put(user.getUserid(), user);
		
	}
	
	@Override
	public List<User> list() {
		List<User> list = new ArrayList<>();
		 @SuppressWarnings("rawtypes")
		Set set = map.entrySet();
		 @SuppressWarnings("rawtypes")
		Iterator it = set.iterator();
		 while(it.hasNext()) {
			 @SuppressWarnings("unchecked")
			Map.Entry<String, User> e = (Entry<String, User>) it.next();
			 list.add(e.getValue());
		 }
		 for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return list;
	}
	

	@Override
	public int count() {

		return map.size();
	}

	@Override
	public User login(User user) {
		User returnuser = null;
		if (map.containsKey(user.getUserid())) {
		//키 = user.getUserid()  일치하는값이 있으면
			User u = detail(user.getUserid()); // 키를가진 user정보를 u 에 넣고
			if (map.get(user.getPasswd()).equals(u.getPasswd())) {
				returnuser = u;
			}
		}
		return returnuser;
	}

	
	@Override
	public User detail(String userid) {
			return (User)map.get(userid);
			//지정된 키의 값(객체)를 반환 못찾으면 null 반환
	}

	@Override
	public boolean update(User user) {
		map.replace(user.getUserid(), user);
		return false;
	}

	@Override
	public boolean remove(String userid) {
		map.remove(userid);
		return false;
	}

	
	
	@Override
	public void saveFile(User user) {

		try {
			File file = new File(FILE_PATH +"list.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			String message = user.toString();
			System.out.println(message);
			writer.write(message);
			writer.newLine();
			writer.flush();
			
		
					
		}catch(Exception e) {
		System.out.println("파일 입력시 에러 발생");	
		}
	}

	@Override
	public List<User> readFile() {
		List<User>  userlist = new ArrayList<>();
		List<String> list = new ArrayList<>();
		try {
			File file = new File(FILE_PATH+"list.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String message = "";
					while((message = reader.readLine()) != null) {
						list.add(message);
					}
					reader.close();
		}catch(Exception e) {
			System.out.println("파일 읽기에서 에러 발생");
			
		}
		User u = new User();
		for (int i = 0; i < list.size(); i++) {
			String[] arr = list.get(i).split(",");
			u.setUserid(arr[0]);
			u.setPasswd(arr[1]);
			u.setName(arr[2]);
			u.setSsn(arr[3]);
			u.setAddr(arr[4]);
			userlist.add(u);
		}
		
				return userlist;
	}

	
	}
	




