package com.micrometer.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.elastic.clients.elasticsearch._types.TimeUnit;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Timer.Sample;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.elastic.ElasticConfig;
import io.micrometer.elastic.ElasticMeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

@SpringBootApplication
public class Micrometer3Application implements CommandLineRunner {

	public static final CompositeMeterRegistry registry = new CompositeMeterRegistry();
	static ElasticConfiguration configuration = new ElasticConfiguration();
	@Autowired
	EmployeeRepo ob;
	
	public static void main(String[] args) {
		SpringApplication.run(Micrometer3Application.class, args);
		Micrometer3Application micrometer3Application = new Micrometer3Application();
		ElasticConfig elasticConfig = configuration.configure();

		ElasticMeterRegistry elasticRegistry = new ElasticMeterRegistry(elasticConfig, Clock.SYSTEM);
		
		MeterRegistry jxmRegistry = new JmxMeterRegistry(new JmxConfig() {
	        @Override
	        public Duration step() {
	            return Duration.ofSeconds(1);
	        }

	        @Override
	        public String get(String s) {
	            return null;
	        }
	    }, Clock.SYSTEM);
		

		registry.add(elasticRegistry);
		registry.add(jxmRegistry);

		for (int j = 1; j <= 17; j++) {

			checkPalindrome("gfpljbRadarbjlpfg");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 1; i<=20; i++) {
			micrometer3Application.insertData(i);
		}
		
		
	}
	
	public void buildCounter() {
		this.counter = Counter.builder("counter").register(registry);
	}
	
	Counter counter;
	
	public Micrometer3Application() {
		buildCounter();
	}
	
	private static void checkPalindrome(String str) {

		Sample s = Timer.start(registry);

		String reverseStr = "";
		int strLength = str.length();
		int store = 0;

		for (int i = (strLength - 1); i >= 0; --i) {
			reverseStr = reverseStr + str.charAt(i);
			for (int j = 0; j < 5; j++) {
				store += j;
			}
		}

		if (str.toLowerCase().equals(reverseStr.toLowerCase())) {
			System.out.println(str + " is a Palindrome String.");
		} else {
			System.out.println(str + " is not a Palindrome String.");
		}

		s.stop(Timer.builder("checkPalindrome").tags("full", "main").register(registry));
	}
	
	public void insertData(int i) {
		Sample s = Timer.start(registry);
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password")) {
			
			String sql = "INSERT INTO employee2 (name, address, salary, commission, date) VALUES (?, ?, ?, ?, ?)";
			 
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "ashik"+i);
			statement.setString(2, "dhaka"+i);
			statement.setDouble(3, 2.2);
			statement.setDouble(4, 2.2);
			statement.setDate(5, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			 
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("A new user was inserted successfully!");
			}
			
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
		s.stop(Timer.builder("InsertData").publishPercentiles(0.5, 0.95, 0.99).tags("full", "main").description("a-description").register(registry));
	}
	
//	public Connection connect() {
//		try {
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", "root", "password");
//			return conn;
//		}catch(SQLException ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
	
	@Override
	public void run(String... args) throws Exception {
		
//		Micrometer3Application micrometer3Application = new Micrometer3Application();
//		micrometer3Application.call(ob);
//		Sample s = Timer.start(registry);
//		System.out.println("working");
//		for(int i=1; i<1000; i++) {
//			Employee first = new Employee("Ashik"+i, "Dhaka"+i, 22.22, 22.22, new Date());
//			ob.save(first);
//		}
//        System.out.println("success");
//        s.stop(Timer.builder("Call").tags("full", "main").register(registry));
		
	}
	
//	public void call(EmployeeRepo ob) {
//		Sample s = Timer.start(registry);
//		System.out.println("working");
//		for(int i=1; i<1000; i++) {
//			Employee first = new Employee("Ashik"+i, "Dhaka"+i, 22.22, 22.22, new Date());
//			ob.save(first);
//		}
//        System.out.println("success");
//        s.stop(Timer.builder("Call").tags("full", "main").register(registry));
//	}

}
