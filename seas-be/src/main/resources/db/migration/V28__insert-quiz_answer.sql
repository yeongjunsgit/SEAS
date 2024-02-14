DELETE
FROM seas.quiz_answer;

-- 데이터베이스
INSERT INTO quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (1, NOW(), NOW(), '기본 키', 1),
       (2, NOW(), NOW(), '프라이머리 키', 1),
       (3, NOW(), NOW(), 'Primary Key', 1),
       (4, NOW(), NOW(), 'PK', 1),

       (5, NOW(), NOW(), '테이블', 2),
       (6, NOW(), NOW(), 'Table', 2),

       (7, NOW(), NOW(), '외래 키', 3),
       (8, NOW(), NOW(), 'Foreign Key', 3),
       (9, NOW(), NOW(), 'FK', 3),

       (10, NOW(), NOW(), '인덱스', 4),
       (11, NOW(), NOW(), 'Index', 4),

       (12, NOW(), NOW(), '트랜잭션', 5),
       (13, NOW(), NOW(), 'Transaction', 5),

       (14, NOW(), NOW(), '스키마', 6),
       (15, NOW(), NOW(), 'Schema', 6),

       (16, NOW(), NOW(), '정규화', 7),
       (17, NOW(), NOW(), 'Normalization', 7),

       (18, NOW(), NOW(), '뷰', 8),
       (19, NOW(), NOW(), 'View', 8),

       (20, NOW(), NOW(), 'SQL', 9),
       (21, NOW(), NOW(), 'Structured Query Language', 9),

       (22, NOW(), NOW(), '트리거', 10),
       (23, NOW(), NOW(), 'Trigger', 10);


-- 네트워크
INSERT INTO seas.quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (24, NOW(), NOW(), '라우터', 11),
       (25, NOW(), NOW(), 'Router', 11),
       (26, NOW(), NOW(), '방화벽', 12),
       (27, NOW(), NOW(), '파이어월', 12),
       (28, NOW(), NOW(), 'Firewall', 12),
       (29, NOW(), NOW(), '서버', 13),
       (30, NOW(), NOW(), 'Server', 13),
       (31, NOW(), NOW(), '와이파이', 14),
       (32, NOW(), NOW(), 'Wireless Fidelity', 14),
       (33, NOW(), NOW(), 'Wi-Fi', 14),
       (34, NOW(), NOW(), 'WiFi', 14),
       (35, NOW(), NOW(), 'SSH', 15),
       (36, NOW(), NOW(), 'Secure Shell', 15),
       (37, NOW(), NOW(), 'TCP', 16),
       (38, NOW(), NOW(), 'Transmission Control Protocol', 16),
       (39, NOW(), NOW(), 'IP', 17),
       (40, NOW(), NOW(), 'Internet Protocol', 17),
       (41, NOW(), NOW(), '게이트웨이', 18),
       (42, NOW(), NOW(), 'Gateway', 18),
       (43, NOW(), NOW(), 'MAC 주소', 19),
       (44, NOW(), NOW(), 'MAC', 19),
       (45, NOW(), NOW(), 'MAC Address', 19),
       (46, NOW(), NOW(), '브리지', 20),
       (47, NOW(), NOW(), 'Bridge', 20),
       (48, NOW(), NOW(), 'UDP', 21),
       (49, NOW(), NOW(), 'User Datagram Protocol', 21),
       (50, NOW(), NOW(), '웹 서버', 22),
       (51, NOW(), NOW(), 'Web Server', 22),
       (52, NOW(), NOW(), 'Web 서버', 22),
       (53, NOW(), NOW(), '웹 Server', 22),
       (54, NOW(), NOW(), 'FTP', 23),
       (55, NOW(), NOW(), 'File Transfer Protocol', 23),
       (56, NOW(), NOW(), '블루투스', 24),
       (57, NOW(), NOW(), 'Bluetooth', 24),
       (58, NOW(), NOW(), '서브넷 마스크', 25),
       (59, NOW(), NOW(), 'Subnet Mask', 25),
       (60, NOW(), NOW(), 'DNS', 26),
       (61, NOW(), NOW(), 'Domain Name System', 26),
       (62, NOW(), NOW(), 'VPN', 27),
       (63, NOW(), NOW(), 'Virtual Private Network', 27),
       (64, NOW(), NOW(), 'LAN', 28),
       (65, NOW(), NOW(), 'Local Area Network', 28),
       (66, NOW(), NOW(), 'WAN', 29),
       (67, NOW(), NOW(), 'Wide Area Network', 29);

-- 컴퓨터구조
INSERT INTO seas.quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (68, NOW(), NOW(), '제어 유닛', 30),
       (69, NOW(), NOW(), 'Control Unit', 30),
       (70, NOW(), NOW(), '시스템 버스', 31),
       (71, NOW(), NOW(), 'System Bus', 31),
       (72, NOW(), NOW(), '캐시', 32),
       (73, NOW(), NOW(), 'Cache', 32),
       (74, NOW(), NOW(), '명령어 세트', 33),
       (75, NOW(), NOW(), 'Instruction Set', 33),
       (76, NOW(), NOW(), '시스템 클럭', 34),
       (77, NOW(), NOW(), 'System Clock', 34),
       (78, NOW(), NOW(), '파이프라인', 35),
       (79, NOW(), NOW(), 'Pipeline', 35),
       (80, NOW(), NOW(), '레지스터', 36),
       (81, NOW(), NOW(), 'Register', 36),
       (82, NOW(), NOW(), '펌웨어', 37),
       (83, NOW(), NOW(), 'Firmware', 37),
       (84, NOW(), NOW(), '인터럽트', 38),
       (85, NOW(), NOW(), 'Interrupt', 38),
       (86, NOW(), NOW(), '메모리', 39),
       (87, NOW(), NOW(), 'Memory', 39);


-- 알고리즘
INSERT INTO seas.quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (88, NOW(), NOW(), '버블 소트', 40),
       (89, NOW(), NOW(), 'Bubble Sort', 40),
       (90, NOW(), NOW(), '다이나믹 프로그래밍', 41),
       (91, NOW(), NOW(), 'Dynamic Programming', 41),
       (92, NOW(), NOW(), '동적 계획법', 41),
       (93, NOW(), NOW(), 'Dynamic Programming', 41),
       (94, NOW(), NOW(), 'DP', 41),
       (95, NOW(), NOW(), '크루스칼', 42),
       (96, NOW(), NOW(), 'Kruskal', 42),
       (97, NOW(), NOW(), '크루스칼 알고리즘', 42),
       (98, NOW(), NOW(), 'Kruskal Algorithm', 42),
       (99, NOW(), NOW(), '<<', 43),
       (100, NOW(), NOW(), '자식', 44),
       (101, NOW(), NOW(), '인접 행렬', 45),
       (102, NOW(), NOW(), '우선순위 큐', 46),
       (103, NOW(), NOW(), 'Priority Queue', 46),
       (104, NOW(), NOW(), '해시', 47),
       (105, NOW(), NOW(), '해쉬', 47),
       (106, NOW(), NOW(), '해시 함수', 47),
       (107, NOW(), NOW(), '해쉬 함수', 47),
       (108, NOW(), NOW(), 'Hash Function', 47),
       (109, NOW(), NOW(), 'Hash', 47),
       (110, NOW(), NOW(), '이진 탐색', 48),
       (111, NOW(), NOW(), 'Binary Search', 48),
       (112, NOW(), NOW(), '깊이 우선 탐색', 49),
       (113, NOW(), NOW(), 'DFS', 49);


-- 운영체제
INSERT INTO seas.quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (114, NOW(), NOW(), '다중 작업', 50),
       (115, NOW(), NOW(), 'Multitasking', 50),
       (116, NOW(), NOW(), '메모리 맵 파일', 51),
       (117, NOW(), NOW(), 'Memory-Mapped File', 51),
       (118, NOW(), NOW(), '스케줄링', 52),
       (119, NOW(), NOW(), 'Scheduling', 52),
       (120, NOW(), NOW(), '파일 시스템', 53),
       (121, NOW(), NOW(), 'File System', 53),
       (122, NOW(), NOW(), '셸', 54),
       (123, NOW(), NOW(), 'Shell', 54),
       (124, NOW(), NOW(), '뮤텍스', 55),
       (125, NOW(), NOW(), 'Mutex', 55),
       (126, NOW(), NOW(), '커널', 56),
       (127, NOW(), NOW(), 'Kernel', 56),
       (128, NOW(), NOW(), '세마포어', 57),
       (129, NOW(), NOW(), 'Semaphore', 57),
       (130, NOW(), NOW(), '페이징', 58),
       (131, NOW(), NOW(), 'Paging', 58),
       (132, NOW(), NOW(), '캐시 메모리', 59),
       (133, NOW(), NOW(), 'Cache Memory', 59),
       (134, NOW(), NOW(), '캐시', 59),
       (135, NOW(), NOW(), 'Cache', 59);


-- 자료구조
INSERT INTO seas.quiz_answer (id, created_at, updated_at, answer, quiz_id)
VALUES (136, NOW(), NOW(), '스택', 60),
       (137, NOW(), NOW(), 'Stack', 60),
       (138, NOW(), NOW(), '배열', 61),
       (139, NOW(), NOW(), 'Array', 61),
       (140, NOW(), NOW(), 'O(1)', 62),
       (141, NOW(), NOW(), 'o(1)', 62),
       (142, NOW(), NOW(), '1', 62),
       (143, NOW(), NOW(), '2', 63),
       (144, NOW(), NOW(), '최소 신장 트리', 64),
       (145, NOW(), NOW(), 'MST', 64),
       (146, NOW(), NOW(), 'Minimum Spanning Tree', 64),
       (147, NOW(), NOW(), '편향 이진 트리', 65),
       (148, NOW(), NOW(), 'Skewed Binary Tree', 65),
       (149, NOW(), NOW(), '힙', 66),
       (150, NOW(), NOW(), 'Heap', 66),
       (151, NOW(), NOW(), '큐', 67),
       (152, NOW(), NOW(), 'Queue', 67),
       (153, NOW(), NOW(), 'Set', 68),
       (154, NOW(), NOW(), '셋', 68),
       (155, NOW(), NOW(), '집합', 68),
       (156, NOW(), NOW(), '그래프', 69),
       (157, NOW(), NOW(), 'Graph', 69);
