package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// BaseTimeEntity 클래스는 모든 Entity의 상위 클래스가 되어 Entity들의
// CreatedDate,ModifiedDate를 자동으로 관리하는 역할을 함

@Getter

//JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 함.
@MappedSuperclass

// BaseTimeEntity 클래스에 Auditing 기능을 포함시킴.
// Audit는 감독하고 검사하다는 뜻으로, 해당 데이터를 보고 있다가 생성 또는 수정이 발생하면 자동으로 값을 넣어주는 기능.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
