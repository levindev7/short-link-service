package org.sls.shortlinkservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class Url {
    @Id
    @Column(unique = true)
    @GeneratedValue
    private int id;

    @Column
    private String originalUrl;

    @Column
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date urlCreationTime = new Date();

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    public Url(){
    }
}
