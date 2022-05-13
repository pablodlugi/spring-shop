package com.pablito.shop.domain.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Audited
@EntityListeners(AuditingEntityListener.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product implements IdentifiedDataSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    private Double price;

    private Long quantity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    private String imagePath;


    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeLong(id);
        objectDataOutput.writeString(name);
        objectDataOutput.writeString(code);
        objectDataOutput.writeDouble(price);
        objectDataOutput.writeLong(quantity);
        objectDataOutput.writeString(imagePath);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        id = objectDataInput.readLong();
        name = objectDataInput.readString();
        code = objectDataInput.readString();
        price = objectDataInput.readDouble();
        quantity = objectDataInput.readLong();
        imagePath = objectDataInput.readString();
    }
}
