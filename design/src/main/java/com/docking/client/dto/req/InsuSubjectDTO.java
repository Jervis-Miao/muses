/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 保险标的（人：被保人/被雇佣人，物：房屋/车）
 * 
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class InsuSubjectDTO implements Serializable {
	private static final long	serialVersionUID	= 4859084452987790667L;

	// -----------------被保人开始--------------------
	/**
	 * 投被保人关系
	 */
	private String				insurantType;

	/**
	 * 被保人姓名
	 */
	private String				name;

	/**
	 * 被保人英文名
	 */
	private String				engName;

	/**
	 * 性别
	 */
	private String				gender;

	/**
	 * 被保人生日(yyyy-MM-dd)
	 */
	private String				birthday;

	/**
	 * 证件类型
	 */
	private String				cardType;

	/**
	 * 证件号
	 */
	private String				cardNo;

	/**
	 * 证件有效期开始时间（yyyy-MM-dd）
	 */
	private String				cardStartTime;

	/**
	 * 证件有效期截止时间（yyyy-MM-dd）
	 */
	private String				cardEndTime;

	/**
	 * 身高（厘米）
	 */
	private BigDecimal			height;

	/**
	 * 体重（公斤）
	 */
	private BigDecimal			weight;

	/**
	 * 从事职业编码
	 */
	private String				jobCode;

	/**
	 * 从事职业等级
	 */
	private String				jobGrade;

	/**
	 * 社保
	 */
	private String				socialSecurity;

	/**
	 * 手机
	 */
	private String				mobile;

	/**
	 * 固定电话
	 */
	private String				tel;

	/**
	 * 阿里通信号
	 */
	private String				aliCall;

	/**
	 * 邮件地址
	 */
	private String				email;

	/**
	 * 国标省
	 */
	private String				province;

	/**
	 * 国标市
	 */
	private String				city;

	/**
	 * 国标区县
	 */
	private String				county;

	/**
	 * 通讯地址
	 */
	private String				postalAddress;

	/**
	 * 邮编
	 */
	private String				zipCode;

	/**
	 * 在读学校
	 */
	private String				schoolName;

	/**
	 * 固定年收入
	 */
	private String				fixedIncome;

	/**
	 * 收入来源
	 */
	private String				incomeSource;

	// -----------------被保人结束--------------------

	// -----------------标的物开始--------------------
	/**
	 * （航延险）航空定票日期
	 */
	private String				bookingTime;

	/**
	 * （航延险）始发地(机场城市CITY)
	 */
	private String				startPlace;

	/**
	 * （航延险）目的地(机场城市CITY)
	 */
	private String				endPlace;

	/**
	 * （航延险）飞机起飞时间
	 */
	private String				startTime;

	/**
	 * （航延险）飞机到达时间
	 */
	private String				endTime;

	/**
	 * （航延险）航班号
	 */
	private String				flightNo;

	/**
	 * （航延险）始发地(机场三字码)
	 */
	private String				startAirCode;

	/**
	 * （航延险）目的地(机场三字码)
	 */
	private String				endAirCode;

	/**
	 * （火车意外险）车次号
	 */
	private String				trainNo;

	/**
	 * （火车意外险）车牌号
	 */
	private String				carno;

	/**
	 * （银行卡盗刷险）开户银行
	 */
	private String				insuBankType;

	/**
	 * （银行卡盗刷险）银行卡号
	 */
	private String				insuBankAccount;

	/**
	 * （宠物险）宠物种类
	 */
	private String				petSpecies;

	/**
	 * （家财险）房屋类型
	 */
	private String				homeType;

	/**
	 * （家财险）财产所在地的新国标省
	 */
	private String				homeProvince;

	/**
	 * （家财险）财产所在地的新国标市
	 */
	private String				homeCity;

	/**
	 * （家财险）财产所在地的新国标区县
	 */
	private String				homeCounty;

	/**
	 * （家财险）财产所在地址
	 */
	private String				homeAddress;

	/**
	 * （驾乘险）车架号
	 */
	private String				chassisNo;

	/**
	 * （驾乘险）发动机号
	 */
	private String				engineNo;

	/**
	 * （驾乘险）座位数
	 */
	private Integer				approvedNum;

	/**
	 * （驾乘险）车辆性质
	 */
	private String				vehicleNature;

	/**
	 * （驾乘险）核载质量（吨）
	 */
	private Integer				loadQuality;
	// -----------------标的物结束--------------------

	/**
	 * 购买份数
	 */
	private Long				quantity;

	/**
	 * 保费
	 */
	private BigDecimal			insuPrice;

	public String getInsurantType() {
		return insurantType;
	}

	public void setInsurantType(String insurantType) {
		this.insurantType = insurantType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStartTime() {
		return cardStartTime;
	}

	public void setCardStartTime(String cardStartTime) {
		this.cardStartTime = cardStartTime;
	}

	public String getCardEndTime() {
		return cardEndTime;
	}

	public void setCardEndTime(String cardEndTime) {
		this.cardEndTime = cardEndTime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAliCall() {
		return aliCall;
	}

	public void setAliCall(String aliCall) {
		this.aliCall = aliCall;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getFixedIncome() {
		return fixedIncome;
	}

	public void setFixedIncome(String fixedIncome) {
		this.fixedIncome = fixedIncome;
	}

	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getStartAirCode() {
		return startAirCode;
	}

	public void setStartAirCode(String startAirCode) {
		this.startAirCode = startAirCode;
	}

	public String getEndAirCode() {
		return endAirCode;
	}

	public void setEndAirCode(String endAirCode) {
		this.endAirCode = endAirCode;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getInsuBankType() {
		return insuBankType;
	}

	public void setInsuBankType(String insuBankType) {
		this.insuBankType = insuBankType;
	}

	public String getInsuBankAccount() {
		return insuBankAccount;
	}

	public void setInsuBankAccount(String insuBankAccount) {
		this.insuBankAccount = insuBankAccount;
	}

	public String getPetSpecies() {
		return petSpecies;
	}

	public void setPetSpecies(String petSpecies) {
		this.petSpecies = petSpecies;
	}

	public String getHomeType() {
		return homeType;
	}

	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

	public String getHomeProvince() {
		return homeProvince;
	}

	public void setHomeProvince(String homeProvince) {
		this.homeProvince = homeProvince;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeCounty() {
		return homeCounty;
	}

	public void setHomeCounty(String homeCounty) {
		this.homeCounty = homeCounty;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public Integer getApprovedNum() {
		return approvedNum;
	}

	public void setApprovedNum(Integer approvedNum) {
		this.approvedNum = approvedNum;
	}

	public String getVehicleNature() {
		return vehicleNature;
	}

	public void setVehicleNature(String vehicleNature) {
		this.vehicleNature = vehicleNature;
	}

	public Integer getLoadQuality() {
		return loadQuality;
	}

	public void setLoadQuality(Integer loadQuality) {
		this.loadQuality = loadQuality;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getInsuPrice() {
		return insuPrice;
	}

	public void setInsuPrice(BigDecimal insuPrice) {
		this.insuPrice = insuPrice;
	}
}
