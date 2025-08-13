package com.lucas.bootbasic.modules.models.test;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestDTO {

    private Long id;

    private String value;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    // Factory method to convert TestEntity to TestDTO
    public static TestDTO fromEntity(TestEntity entity) {
        TestDTO dto = new TestDTO();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setCreateTime(entity.getCreateTime());
        dto.setUpdateTime(entity.getUpdateTime());
        return dto;
    }
}
