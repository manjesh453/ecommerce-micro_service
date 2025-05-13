package com.productservice.entity;


import com.productservice.shared.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Version
    private Long version = 0L;

    @Column(name="post_title")
    private String title;

    @Column(length=10000)
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;

    private String imageName;

    private Long categoryId;

    private Long userId;

    private Long likes;

}
