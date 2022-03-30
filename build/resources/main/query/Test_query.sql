# AWS RDS 생성 시 데이터베이스 명
use freelec_springboot2_webservice;

# 현재 DB 케릭터셋 설정 확인
show variables like 'c%';

# 케릭터셋 변경
ALTER database freelec_springboot2_webservice
character set = 'utf8mb4'
collate = 'utf8mb4_general_ci';

# 타임존 확인 / 변경 안됬을시 DB 서버 재부팅
select @@time_zone, now();

# 한글 데이터 등록확인
CREATE TABLE test(
                     id bigint(20) NOT NULL AUTO_INCREMENT,
                     content varchar(225) default null,
                     primary key (id)
) ENGINE = InnoDB;

insert into test(content) values('테스트');

select * from test;# AWS RDS 생성 시 데이터베이스 명
use freelec_springboot2_webservice;

# 현재 DB 케릭터셋 설정 확인
show variables like 'c%';

# 케릭터셋 변경
ALTER database freelec_springboot2_webservice
character set = 'utf8mb4'
collate = 'utf8mb4_general_ci';

# 타임존 확인 / 변경 안됬을시 DB 서버 재부팅
select @@time_zone, now();

# 한글 데이터 등록확인
CREATE TABLE test(
                     id bigint(20) NOT NULL AUTO_INCREMENT,
                     content varchar(225) default null,
                     primary key (id)
) ENGINE = InnoDB;

insert into test(content) values('테스트');

select * from test;