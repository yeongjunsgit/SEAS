# 기본 이미지로 Redis를 사용합니다.
FROM redis:latest

# Redis 서버가 사용할 포트를 노출합니다.
EXPOSE 6379

# Docker 컨테이너가 시작될 때 실행할 명령을 지정합니다.
CMD ["redis-server"]