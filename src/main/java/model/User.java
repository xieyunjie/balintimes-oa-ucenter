package model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
	private static final long serialVersionUID = -8567418333741144420L;

	private String uid;
	private String username;
	private String password;
	private boolean isadmin;
	private String employeename;
	private boolean isenable;
	private boolean delflag;
	private String usertype;
	private String usertypename;
	private String email;
	private String comment;
	private String createby;
	private Date createtime;
	private String editby;
	private Date edittime;
	private Date lastlogin;
	private int sex;
	private Date birthday;
	private String idcard;
	private String address;
	private String mobile;
	private String telephone;
	private String emergencycontact;
	private String avatarurl;
	private String postuid;	
	private String postname;
	private String parentuid;
	private String parentname;
	private String userpostuid;
	
	public User()
	{
		this.uid = "0";
		this.isenable = true;
		this.delflag = false;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isIsadmin()
	{
		return isadmin;
	}

	public void setIsadmin(boolean isadmin)
	{
		this.isadmin = isadmin;
	}

	public String getEmployeename()
	{
		return employeename;
	}

	public void setEmployeename(String employeename)
	{
		this.employeename = employeename;
	}

	public boolean isIsenable()
	{
		return isenable;
	}

	public void setIsenable(boolean isenable)
	{
		this.isenable = isenable;
	}

	public boolean isDelflag()
	{
		return delflag;
	}

	public void setDelflag(boolean delflag)
	{
		this.delflag = delflag;
	}

	public String getUsertype()
	{
		return usertype;
	}

	public void setUsertype(String usertype)
	{
		this.usertype = usertype;
	}

	public String getUsertypename()
	{
		return usertypename;
	}

	public void setUsertypename(String usertypename)
	{
		this.usertypename = usertypename;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getCreateby()
	{
		return createby;
	}

	public void setCreateby(String createby)
	{
		this.createby = createby;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}

	public String getEditby()
	{
		return editby;
	}

	public void setEditby(String editby)
	{
		this.editby = editby;
	}

	public Date getEdittime()
	{
		return edittime;
	}

	public void setEdittime(Date edittime)
	{
		this.edittime = edittime;
	}

	public Date getLastlogin()
	{
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin)
	{
		this.lastlogin = lastlogin;
	}
	
	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getIdcard() {
		return idcard;
	}
	
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmergencycontact() {
		return emergencycontact;
	}
	
	public void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}
	
	public String getAvatarurl() {
		return avatarurl;
	}
	
	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}
	
	public String getPostuid() {
		return postuid;
	}
	
	public void setPostuid(String postuid) {
		this.postuid = postuid;
	}

	public void setParentuid(String parentuid) {
		this.parentuid = parentuid;
	}
	
	public String getParentuid() {
		return parentuid;
	}
	
	public void setPostname(String postname) {
		this.postname = postname;
	}
	
	public String getPostname() {
		return postname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	
	public String getParentname() {
		return parentname;
	}
	
	public void setUserpostuid(String userpostuid) {
		this.userpostuid = userpostuid;
	}
	
	public String getUserpostuid() {
		return userpostuid;
	}
	
	@Override
	public String toString() {
		return uid + " ## " + username;
	}
}
