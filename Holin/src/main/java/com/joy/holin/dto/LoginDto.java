package com.joy.holin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 這是 Jackson 解析 JSON 必備的空建構子
@AllArgsConstructor
public class LoginDto {

	private String email,pwd;
}
