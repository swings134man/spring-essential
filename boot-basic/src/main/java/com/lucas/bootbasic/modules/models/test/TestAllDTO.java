package com.lucas.bootbasic.modules.models.test;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestAllDTO {

    // 기본 필드
    private Long id;
    private String value;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    // Request DTO
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String value;

        public TestEntity toEntity() {
            TestEntity entity = new TestEntity();
            entity.setId(this.id);
            entity.setValue(this.value);

            return entity;
        }
    }

    // Response DTO (ALl Fields)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String value;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public static Response fromEntity(TestEntity entity) {
            return Response.builder()
                    .id(entity.getId())
                    .value(entity.getValue())
                    .createTime(entity.getCreateTime())
                    .updateTime(entity.getUpdateTime())
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ValueResponse {
        private String value;
    }

}
