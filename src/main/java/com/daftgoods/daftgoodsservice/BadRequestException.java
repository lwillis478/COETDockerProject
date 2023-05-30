package com.daftgoods.daftgoodsservice;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class BadRequestException extends RuntimeException {
}
