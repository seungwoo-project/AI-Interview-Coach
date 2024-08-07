# Open AI를 이용한 자기소개서 기반 가상 면접 도우미

## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [주요 기능](#주요-기능)
3. [시스템 아키텍처](#시스템-아키텍처)
4. [데이터베이스 설계](#데이터베이스-설계)
5. [Controller 매핑 정보](#Controller-매핑-정보)
6. [화면 구성](#화면-구성)
7. [개발 후기](#개발-후기)
8. [라이센스](#라이센스)

## 프로젝트 개요

"AI를 활용한 자기소개서 기반 가상 면접 도우미" 프로젝트를 소개하고자 합니다. 이 프로젝트는 면접 준비에 어려움을 겪는 사용자들을 돕기 위해 기획되었습니다. 사용자가 자신의 자기소개서를 입력하면 AI가 이를 분석하여 예상 면접 질문을 생성해주는 서비스로, 실제 면접 상황에 더욱 효과적으로 대비할 수 있도록 지원하는 프로젝트 입니다.
- **개발 기간**: 2024.05.28 ~ 
- **개발 인원**: 2명 (백엔드 개발)
- **주요 기능**: 회원 관리, 자기소개서 CRUD, AI 면접 질문 생성, 가상 면접 진행
- **기술 스택**: 
  - Backend: Java, Spring Boot, Spring Data JPA
  - Frontend: Thymeleaf, HTML, CSS, JavaScript
  - Database: H2
  - API: Open AI API

## 주요 기능

1. **회원 관리**
   - 회원가입 및 로그인 기능
   - 유효성 검증 기능

2. **자기소개서 관리**
   - 자기소개서 등록, 조회, 삭제
   - 파일 업로드 지원

3. **면접 질문 생성 및 관리**
   - Open AI API를 활용한 자기소개서 기반 면접 질문 자동 생성
   - 사용자 직접 질문 추가 기능
   - 생성된 질문 저장 및 관리

4. **가상 면접 진행**
   - 저장된 질문을 바탕으로 가상 면접 진행
   - 면접 결과 저장 및 다시보기 기능

5. **사용자 친화적 UI/UX**
   - 직관적인 인터페이스 제공
   - 반응형 웹 디자인

## 시스템 아키텍처

```
[Client]
    ↕ HTTP
[Spring Boot Application]
    |
    ├─ [Controllers]
    |   ├─ UserController
    |   ├─ InterviewController
    |   └─ InterviewReadyController
    |
    ├─ [Services]
    |   ├─ UserService
    |   ├─ CoverLetterService
    |   └─ QuestionService
    |
    ├─ [Repositories]
    |   ├─ UserRepository
    |   ├─ CoverLetterRepository
    |   └─ QuestionRepository
    |
    └─ [Entities]
        ├─ User
        ├─ CoverLetter
        └─ Question

[Database (H2 or other)]

[External Services]
    ↕ API
[OpenAI API]
```

## 데이터베이스 설계


![스크린샷 2024-06-15 오전 1 36 36](https://github.com/seungwoo-project/AI-Interview-Coach/assets/112483124/69ab60ba-f716-4803-8e8f-985cadbf3f72)


## Controller 매핑 정보

### 사용자 관리

| 기능 | Method | URL | 설명 |
|------|:------:|-----|------|
| 로그인 폼 | GET | `/login` | 로그인 페이지 반환 |
| 로그인 처리 | POST | `/login` | 로그인 처리 후 성공 시 목록, 실패 시 로그인 페이지 반환 |
| 로그아웃 | GET | `/logout` | 세션 종료 후 로그인 페이지로 리다이렉트 |
| 회원가입 폼 | GET | `/register` | 회원가입 페이지 반환 |
| 회원가입 처리 | POST | `/register` | 회원가입 처리 후 성공 시 홈, 실패 시 회원가입 페이지 반환 |

### 자기소개서 관련

| 기능 | Method | URL | 설명 |
|------|:------:|-----|------|
| 목록 조회 | GET | `/list` | 자기소개서 목록 페이지 반환 |
| 상세 조회 | GET | `/list/{coverLetterId}` | 특정 자기소개서 상세 페이지 반환 |
| 업로드 | POST | `/upload` | 자기소개서 파일 업로드 후 목록으로 리다이렉트 |
| 삭제 | POST | `/list/{coverLetterId}/delete` | 특정 자기소개서 삭제 후 목록으로 리다이렉트 |

### 면접 질문 관련

| 기능 | Method | URL | 설명 |
|------|:------:|-----|------|
| 질문 선택 | GET | `/list/{coverLetterId}/select` | 질문 선택 페이지 반환 |
| 질문 삭제 | GET | `/list/{questionId}/select/delete` | 특정 질문 삭제 후 선택 페이지로 리다이렉트 |
| 선택 질문 저장 | POST | `/list/{coverLetterId}/select` | 선택된 질문을 세션에 저장 |
| 사용자 정의 질문 추가 | POST | `/list/{coverLetterId}/userSelect` | 사용자 정의 질문을 세션에 저장 (AJAX) |

### 면접 진행 관련

| 기능 | Method | URL | 설명 |
|------|:------:|-----|------|
| 로딩 화면 | GET | `/list/{coverLetterId}/loading` | 면접 준비 로딩 페이지 반환 |
| 면접 시작 | POST | `/list/{coverLetterId}/loading` | 면접 질문 생성 및 면접 페이지로 리다이렉트 |
| 면접 화면 | GET | `/list/{coverLetterId}/interview` | 면접 진행 메인 페이지 반환 |
| 면접 종료 | GET | `/list/{coverLetterId}/interview/finish` | 면접 종료 페이지 반환 |
| 질문 저장 목록 | GET | `/list/{coverLetterId}/interview/save` | 저장할 질문 목록 페이지 반환 |
| 선택 질문 저장 | POST | `/list/{coverLetterId}/interview/save` | 선택된 질문을 DB에 저장 후 목록으로 리다이렉트 |

## 화면 구성

1. 로그인 화면
2. 회원가입 화면
3. 자기소개서 목록 화면
4. 자기소개서 상세 조회 화면
5. 면접 준비 화면 (질문 선택, 추가)
6. AI 면접 진행 화면
7. 면접 결과 및 다시보기 화면
8. 질문 저장 화면

## 개발 후기

- Spring, JPA, Thymeleaf 등의 기술을 실제 프로젝트에 적용하며 실력 향상
- 프로젝트 설계의 중요성 인식 및 체계적인 설계 방법 학습
- Open AI API 연동 등 새로운 기술에 대한 도전과 성공 경험
- 팀 프로젝트 Git을 통해 협업 능력 향상
- 실제 서비스 개발 과정에서의 문제 해결 능력 향상

## 라이센스

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
