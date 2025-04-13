package org.dainn.searchservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users")
public class User {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String avatarUrl;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String role;

    @Field(type = FieldType.Keyword)
    private String agencyId;

    @Field(type = FieldType.Nested)
    private List<User.Permission> permissions;

    @Field(type = FieldType.Nested)
    private List<User.SubAccount> subAccounts;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Permission {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Keyword)
        private String subAccountId;

        @Field(type = FieldType.Boolean)
        private Boolean access;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubAccount {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Keyword)
        private String agencyId;
    }
}