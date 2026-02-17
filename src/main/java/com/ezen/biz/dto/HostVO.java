package com.ezen.biz.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class HostVO {
	
	// Fields
	@NotBlank(message = "Host email is required.")
	@Email(message = "Host email format is invalid.")
	@Size(max = 100, message = "Host email is too long.")
	private String hemail;
	@NotBlank(message = "Password is required.")
	@Size(min = 8, max = 64, message = "Password must be 8 to 64 characters.")
	private String pwd;
	@NotBlank(message = "Phone is required.")
	@Pattern(regexp = "^[0-9\\-]{8,20}$", message = "Phone format is invalid.")
	private String phone;
	@NotBlank(message = "Name is required.")
	@Size(max = 50, message = "Name is too long.")
	private String name;
	private int status;

	// Getter, Setter 메소드
	public String getHemail() {
		return hemail;
	}

	public void setHemail(String hemail) {
		this.hemail = hemail;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// ToString
	@Override
	public String toString() {
		return "HostVO [hemail=" + hemail + ", pwd=" + pwd + ", phone=" + phone + ", name=" + name + ", status="
				+ status + "]";
	}
	
}//HostVO
