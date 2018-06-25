package utils.area.dto;

import lombok.Data;

@Data
public class FeeMode {
	// 年龄
	private int		age;
	// 性别
	private Short	sex;
	// 缴费期间
	private String	payPeriod;
	// 保险期间
	private String	insurPeriod;
	// 保费费率
	private double	fee;
	// 主险费率
	private double	mainFee;
	// 附加险费率
	private double	subFee;
	// 保额
	private int		amount;
	// 保单价格
	private Double	price;

	public FeeMode() {
	}

	public FeeMode(int age, Short sex, String payPeriod, String insurPeriod, double fee, double mainFee, double subFee,
			int amount, Double price) {
		this.age = age;
		this.sex = sex;
		this.payPeriod = payPeriod;
		this.insurPeriod = insurPeriod;
		this.fee = fee;
		this.mainFee = mainFee;
		this.subFee = subFee;
		this.amount = amount;
		this.price = price;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new FeeMode(this.age, this.sex, this.payPeriod, this.insurPeriod, this.fee, this.mainFee, this.subFee,
				this.amount, this.price);
	}
}
