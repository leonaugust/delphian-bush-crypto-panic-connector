package com.delphian.bush.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    private String code;
    private String title;
    private String slug;
    private String url;

}
