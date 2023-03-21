package com.forpawchain.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCTOR_LICENSE")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorLicenseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "dsnm", nullable = false)
	private String name; // 대상자 이름

	/**
	 * 주민등록번호
	 * "-"를 제외한 13자리
	 */
	@Column(name = "jumin", nullable = false)
	private String registnum;

	/**
	 * 전화번호
	 * "-"없이 입력
	 * 최대 12자리
	 */
	@Column(name = "phonenum", nullable = false)
	private String tel;

	/**
	 * 통신사 구분
	 * 1: KT, 2: SKT, 3: LG, 4: KT알뜰폰, 5: SKT알뜰폰, 6: LG알뜰폰
	 */
	@Column(name = "telecomgubun", nullable = false)
	private int telecom;

	public DoctorLicenseEntity(String name, String registnum, String tel, int telecom) {
		this.name = name;
		this.registnum = registnum;
		this.tel = tel;
		this.telecom = telecom;
	}
}
