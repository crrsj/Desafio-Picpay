package br.com.picpay.dto;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value, Long senderId,Long receiverId) {

}
