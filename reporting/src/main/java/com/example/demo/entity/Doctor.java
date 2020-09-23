package com.example.demo.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component //生成当前类的实例对象存到IOC容器中
//@ConfigurationProperties(prefix = "doctor")//将配置文件前缀名为doctor的属性值映射到当前类的变量上
@Document(collection  = "doctor")
//@CompoundIndex(def= "{'mail':1,'name':-1}")//复合索引，mail升序，name降序
//定义序列化(object),屏蔽了操作系统的差异，字节顺序等
public class Doctor implements Serializable {
	@Id
	private String id;//主键
//	@Field("mail")//若变量名与域名（即列名）不一样可以强制映射
	private String mail;
//	@Indexed//添加一个单字段索引
	private String name;
	private String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Doctor [id=" + id + ", mail=" + mail + ", name=" + name + ", password=" + password + "]";
	}
	
	
}
