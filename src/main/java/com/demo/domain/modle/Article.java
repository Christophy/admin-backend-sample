package com.demo.domain.modle;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by
 * 用户
 *
 */
@TableName("article")
public class Article implements Serializable {


	private static final long serialVersionUID = -3469861468131688858L;

	/** 主键ID */
	@TableId
	private String id = UUID.randomUUID().toString();

	private String title;

	private String content;

	private Integer status;

	/** 创建时间 */
	private String publisher;

	/** 创建时间 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createTime;

	/** 最后登录时间 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date lastModified;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
