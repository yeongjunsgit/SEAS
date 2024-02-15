-- Insert data into seas.flashcard
INSERT INTO seas.flashcard (id, keyword, created_at, updated_at, category_id)
VALUES
    (1, '기본키 (Primary Key)', NOW(), NOW(), 1),
    (2, '테이블 (Table)', NOW(), NOW(), 1),
    (3, '외래 키 (Foreign Key)', NOW(), NOW(), 1),
    (4, '인덱스 (Index)', NOW(), NOW(), 1),
    (5, '트랜잭션 (Transaction)', NOW(), NOW(), 1),
    (6, '스키마 (Schema)', NOW(), NOW(), 1),
    (7, '정규화 (Normalization)', NOW(), NOW(), 1),
    (8, '뷰 (View)', NOW(), NOW(), 1),
    (9, 'SQL; Structured Query Language', NOW(), NOW(), 1),
    (10, '트리거 (Trigger)', NOW(), NOW(), 1);

-- Insert data into seas.flashcard_content
INSERT INTO seas.flashcard_content (id, content, created_at, updated_at, flashcard_id)
VALUES
    (1, '관계형 데이터베이스에서 조(레코드)의 식별자로 이용하기에 가장 적합한 것을 관계 (테이블)마다 단 한 설계자에 의해 선택, 정의된 후보 키', NOW(), NOW(), 1),
    (2, '행과 열의 형식으로 보관된 관련 데이터의 모음', NOW(), NOW(), 2),
    (3, '지정된 수의 열이 있지만 행 수에는 제한이 없음', NOW(), NOW(), 2),
    (4, '한 테이블의 필드(attribute) 중 다른 테이블의 행(row)을 식별할 수 있는 키', NOW(), NOW(), 3),
    (5, '다른 테이블의 기본 키를 참조하여 두 테이블을 연결하는 테이블의 속성 집합', NOW(), NOW(), 3),
    (6, '테이블에 대한 동작의 속도를 높여주는 자료 구조', NOW(), NOW(), 4),
    (7, '테이블의 모든 행을 검색할 필요 없이 데이터를 빠르게 찾는 데 사용', NOW(), NOW(), 4),
    (8, '데이터베이스 내에서 수행되는 작업 단위', NOW(), NOW(), 5),
    (9, '각각의 트랜잭션에 대해 원자성(Atomicity), 일관성(Consistency), 독립성(Isolation), 영구성(Durability)을 보장', NOW(), NOW(), 5),
    (10, '데이터베이스에서 자료의 구조, 자료의 표현 방법, 자료 간의 관계를 형식 언어로 정의한 구조', NOW(), NOW(), 6),
    (11, '데이터베이스의 데이터 구성을 나타냄', NOW(), NOW(), 6),
    (12, '관계형 데이터베이스의 설계에서 중복을 최소화하게 데이터를 구조화하는 프로세스', NOW(), NOW(), 7),
    (13, '정규화란 크고, 제대로 조직되지 않은 테이블들과 관계들을 작고 잘 조직된 테이블과 관계들로 나누는 것', NOW(), NOW(), 7),
    (14, '기본 테이블로부터 파생하여 만든 가상의 테이블', NOW(), NOW(), 8),
    (15, '실제 테이블과 달리 데이터 자체를 포함하고 있는 것은 아님', NOW(), NOW(), 8),
    (16, '관계형 데이터베이스 관리 시스템(RDBMS)의 데이터를 관리하기 위해 설계한 특수 목적의 프로그래밍 언어', NOW(), NOW(), 9),
    (17, '데이터 쿼리, 데이터 조작(insert, update, and delete), 데이터 정의(스키마 생성 및 수정), 데이터 액세스 제어가 포함', NOW(), NOW(), 9),
    (18, '테이블에 대한 이벤트에 반응해 자동으로 실행되는 작업', NOW(), NOW(), 10),
    (19, '데이터 작업 제한, 작업 기록, 변경 작업 감사 등을 할 수 있음', NOW(), NOW(), 10);