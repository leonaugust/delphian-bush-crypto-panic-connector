package com.delphian.bush.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsSource {

    private String title;
    private String region;
    private String domain;
    private String path;

}
