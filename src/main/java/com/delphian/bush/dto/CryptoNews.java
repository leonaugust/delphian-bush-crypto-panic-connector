package com.delphian.bush.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoNews {

    private String kind;

    private String domain;

    private NewsSource source;

    private String title;

    @JsonProperty("published_at")
    private String publishedAt;

    private String slug;

    private String id;

    private String url;

    @JsonProperty("created_at")
    private String createdAt;

    private List<Currency> currencies;

}