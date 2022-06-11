package com.delphian.bush.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    private List<Currencies> currencies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoNews that = (CryptoNews) o;
        return Objects.equals(slug, that.slug) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, id);
    }

}