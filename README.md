# 📁 파일 확장자 차단 과제

보안 위험이 있는 실행 파일(`exe`, `sh` 등)의 업로드를 방지하기 위해 파일 확장자 기반의 차단 기능을 설계하고 구현하였습니다.


---

## 📋 기능 명세서

> 확장자 차단 기능의 상세 요구사항

![기능 명세서](https://github.com/user-attachments/assets/f77bb4ec-18a7-4270-9a29-39caaeee5b00)

---

## 💭 고민한 부분

- **카카오 소셜로그인**  
  → 사용자별로 확장자 차단 기능을 적용하기 위해 회원 기능을 도입하였습니다.

- **커스텀 확장자 등록 수 제한 (200개 → 10개)**  
  → 과제 검토의 편의성을 위해 커스텀 확장자 최대 등록 수를 10개로 축소하였습니다.

- **대소문자 중복 처리**  
  → `exe`와 `EXE`처럼 대소문자로 구분했습니다.  
  → Windows에서는 대소문자를 구분하지 않지만, Linux 환경에서는 구분되므로 이를 고려해 처리하였습니다.

- **고정 확장자와 커스텀 확장자 간 중복 방지**  
  → 커스텀 확장자 등록 시, 기존 고정/커스텀 확장자와의 중복 여부를 백엔드에서 JPQL로 검증하여 예외를 처리하도록 구현하였습니다.

- **입력값 유효성 검사**  
  → 실제 확장자에는 공백, 특수기호, 한글, 마침표(`.`) 등이 포함되지 않기 때문에 정규식을 활용하여 영문 대/소문자 및 숫자만 허용하도록 처리하였습니다.

---

## 🗂 ERD

> 데이터베이스 구조 및 테이블 간 관계

![ERD](https://github.com/user-attachments/assets/7713cac1-ffb1-4566-92c9-b2623dfe2aa3)

---

## 🏗 시스템 아키텍처

> 프론트엔드 - 백엔드 - 데이터베이스 간 흐름 구조

![System Architecture](https://github.com/user-attachments/assets/91aff82a-c42a-4f74-a11b-c17893862309)

---

## 🛠 사용 기술 스택

- **Backend:** Spring Boot 3.3, Spring Data JPA, Spring Cloud 등
- **Frontend:** Thymeleaf, jQuery (AJAX)
- **Database:** AWS RDS (MySQL)
- **Infra:** AWS EC2, Nginx, Docker, RDS
- **Deployment:** Docker Compose, GitHub Actions (CD)
- **Rendering: SSR (Server Side Rendering) - Thymeleaf 기반 템플릿 렌더링 사용**
  → 페이지 규모가 크지 않고, 개발 시간이 부족해 빠르게 구현 가능한 SSR 방식(Thymeleaf) 을 선택하였습니다.

---

## ✨ 느낀 점

- 단순한 확장자 차단 기능이지만, 사용자 편의성 고민을 많이 했습니다.
- 대소문자 처리, 공백 및 특수문자 대응 등 다양한 예외 상황을 고려하면서 **입력값 정제와 사용자 입력 검증의 중요성**을 다시 한 번 느낄 수 있었습니다.

---

## 🔖 커밋 컨벤션

| Type       | Description                                     |
|------------|-------------------------------------------------|
| **feat**   | 새로운 기능 추가                                |
| **bug**    | 버그 수정                                       |
| **fix**    | 기능 변경 없이 코드 구조 개선                   |
| **test**   | 테스트 코드 작성                                |
| **docs**   | 문서 작성 또는 수정                             |

