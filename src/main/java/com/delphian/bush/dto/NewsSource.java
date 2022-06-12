package com.delphian.bush.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsSource {

    private String title;
    private String region;
    private String domain;
    private String path;

}
