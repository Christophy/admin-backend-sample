package com.demo.domain.modle;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by
 * 用户
 *
 */
@TableName("user")
public class User implements Serializable {


	private static final long serialVersionUID = 4777536936286713420L;

	/** 主键ID */
	@TableId
	private String id = UUID.randomUUID().toString();

	/** 登录名称 */
	private String username;

	/** 密码 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;



	/** 是否禁用 */
	private boolean disabled;

	/** 创建时间 */
	private Date createTime;

	/** 最后登录时间 */
	private Date lastModified;

	private String role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
