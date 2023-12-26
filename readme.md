# 할일

## 회원작업

- [x] GET /member/join : 가입 폼
- [x] POST /member/join : 가입 폼 처리
- [x] GET /member/login : 로그인 폼
- [x] POST /member/login : 로그인 폼 처리
- [x] POST /member/logout : 로그아웃

## 글 작업

- [x] GET / : 홈
    - 최신글 30개 노출
- [ ] GET /post/list : 전체 글 리스트
    - 공개된 글만 노출
- [ ] GET /post/myList : 내 글 리스트
    - 내 글 목록 조회
- [x] GET /post/1 : 1번 글 상세보기
- [ ] GET /post/write : 글 작성 폼
- [ ] POST /post/write : 글 작성 처리
- [ ] GET /post/1/modify : 1번 글 수정 폼
- [ ] PUT /post/1/modify : 1번 글 수정 폼 처리
- [ ] DELETE /post/1/delete : 1번 글 삭제

## 블로그 작업

- [ ] GET /b/user1 : 회원 user1 의 전체 글 리스트
    - 특정 회원의 글 모아보기
- [ ] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기
