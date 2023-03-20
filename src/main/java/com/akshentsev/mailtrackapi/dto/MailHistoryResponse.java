package com.akshentsev.mailtrackapi.dto;

import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailHistoryResponse {
    private List<MailHistoryEntity> history;
}
