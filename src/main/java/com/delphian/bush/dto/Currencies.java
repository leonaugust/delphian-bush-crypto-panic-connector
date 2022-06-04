package com.delphian.bush.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currencies {

    private String code;
    private String title;
    private String slug;
    private String url;

}
