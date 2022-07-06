package com.keeb.productservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

}
