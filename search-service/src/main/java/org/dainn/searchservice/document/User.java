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

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Permission> permissions;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Agency {
        @Field(type = FieldType.Keyword)
        private String id;

        @Field(type = FieldType.Text)
        private String name;

        @Field(type = FieldType.Text)
        private String companyLogo;

        @Field(type = FieldType.Keyword)
        private String connectAccountId;

        @Field(type = FieldType.Keyword)
        private String customerId;

        @Field(type = FieldType.Text)
        private String companyEmail;

        @Field(type = FieldType.Keyword)
        private String companyPhone;

        @Field(type = FieldType.Boolean)
        private Boolean whiteLabel;

        @Field(type = FieldType.Text)
        private String address;

        @Field(type = FieldType.Text)
        private String city;

        @Field(type = FieldType.Text)
        private String zipCode;

        @Field(type = FieldType.Text)
        private String state;

        @Field(type = FieldType.Text)
        private String country;

        @Field(type = FieldType.Integer)
        private Integer goal;

        @Field(type = FieldType.Nested)
        private List<SidebarOption> options;

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

        @Field(type = FieldType.Text)
        private String name;

        @Field(type = FieldType.Keyword)
        private String connectAccountId;

        @Field(type = FieldType.Text)
        private String subAccountLogo;

        @Field(type = FieldType.Text)
        private String companyEmail;

        @Field(type = FieldType.Keyword)
        private String companyPhone;

        @Field(type = FieldType.Text)
        private String address;

        @Field(type = FieldType.Integer)
        private Integer goal;

        @Field(type = FieldType.Text)
        private String city;

        @Field(type = FieldType.Text)
        private String state;

        @Field(type = FieldType.Text)
        private String country;

        @Field(type = FieldType.Text)
        private String zipCode;

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