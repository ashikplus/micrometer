package com.micrometer.demo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String address;
	Double salary;
	Double commission;
	Date date;

	Employee() {
	}

	Employee(String name, String address, Double salary, Double commission, Date date) {
		this.name = name;
		this.address = address;
		this.salary = salary;
		this.commission = commission;
		this.date = date;
	}
}
