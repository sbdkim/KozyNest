package com.ezen.biz.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberVO {
	
	// Fields
	@NotBlank(message = "Email is required.")
	@Email(message = "Email format is invalid.")
	@Size(max = 100, message = "Email is too long.")
	private String email;
	@NotBlank(message = "Password is required.")
	@Size(min = 8, max = 64, message = "Password must be 8 to 64 characters.")
	private String pwd;
	@NotBlank(message = "Name is required.")
	@Size(max = 50, message = "Name is too long.")
	private String name;
	@NotBlank(message = "Phone is required.")
	@Pattern(regexp = "^[0-9\\-]{8,20}$", message = "Phone format is invalid.")
	private String phone;
	private Date joindate;

	// Getter, Setter 메소드
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	// ToString
	@Override
	public String toString() {
		return "MemberVO [email=" + email + ", pwd=" + pwd + ", name=" + name + ", phone=" + phone + ", joindate="
				+ joindate + ", getEmail()=" + getEmail() + ", getPwd()=" + getPwd() + ", getName()=" + getName()
				+ ", getPhone()=" + getPhone() + ", getJoindate()=" + getJoindate() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}//MemberVO
