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

    @Field(type = FieldType.Nested, includeInParent = true)
    private Agency agency;

    @Field(type = FieldType.Nested)
    private List<Permission> permissions;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Agency {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Text)
        private String companyLogo;

        @Field(type = FieldType.Nested)
        private List<SubAccount> subAccounts;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Permission {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Nested)
        private SubAccount subAccount;

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
        private String name;

        @Field(type = FieldType.Nested)
        private List<SidebarOption> options;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SidebarOption {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Keyword)
        private String link;

        @Field(type = FieldType.Keyword)
        private String icon;
    }
}