# application.properties Git 추적 문제 해결

## 📋 목차
1. [문제 상황](#문제-상황)
2. [원인 분석](#원인-분석)
3. [해결 방법](#해결-방법)
4. [검증](#검증)
5. [팀원 안내사항](#팀원-안내사항)

---

## 🚨 문제 상황

### 증상
- `dev` 브랜치에서 `git pull` 실행 시 `application.properties` 파일이 삭제됨
- `.gitignore`에 `application.properties`를 추가했음에도 문제 지속
- EC2 인스턴스 정보 등 민감한 설정이 포함되어 있어 Git으로 관리하면 안 됨

### 환경
- **브랜치**: `dev`
- **파일 경로**: `src/main/resources/application.properties`
- **저장소**: `chimugreen/green_server`

---

## 🔍 원인 분석

### Git 커밋 히스토리 분석

```bash
$ git log --all --oneline -- '*application.properties'

9142cfa Delete src/main/resources/application.properties  ← 문제의 커밋
c0aabe7 fix: request가 전부 401로 내려오던 오류 수정
ecd8b5c 다시 수정 완료
26b9b8e Feat db설정,network설정 (#1)
fca065a Spring boot 프로젝트 생성 및 깃이그노어 추가
```

### 문제의 핵심

```
┌─────────────────────────────────────────────────────────────────┐
│                      문제 발생 과정                              │
└─────────────────────────────────────────────────────────────────┘

Step 1: 초기 상태
├─ application.properties가 Git에 추적됨
└─ 민감 정보 포함 (EC2 인스턴스 정보 등)

Step 2: .gitignore 추가
├─ .gitignore에 application.properties 추가
└─ ❌ 하지만 이미 Git에 추적 중인 파일은 무시되지 않음!

Step 3: 파일 삭제 커밋 (9142cfa)
├─ Git 저장소에서 파일 삭제
└─ 이 삭제 커밋이 dev 브랜치에 포함됨

Step 4: 문제 발생
├─ git pull 실행 시
└─ Git이 "삭제 커밋"을 적용하여 로컬 파일 삭제
```

### 왜 .gitignore가 작동하지 않았나?

```
┌─────────────────────────────────────────────────────────────┐
│  .gitignore는 "아직 추적되지 않은 파일"만 무시합니다         │
│                                                               │
│  이미 Git에 추적 중인 파일 → .gitignore 무시됨 ❌            │
│  새로운 파일 → .gitignore 적용됨 ✅                          │
└─────────────────────────────────────────────────────────────┘
```

---

## ✅ 해결 방법

### 전략

**"삭제 커밋을 되돌린 후, Git 추적만 중지"**

이렇게 하면:
1. 삭제 커밋이 취소되어 pull 시 파일이 삭제되지 않음
2. Git은 더 이상 이 파일을 추적하지 않음
3. 로컬 파일은 그대로 유지됨

### 구현 과정

```mermaid
graph TD
    A[dev 브랜치 이동] --> B[삭제 커밋 revert]
    B --> C[git rm --cached로 추적 중지]
    C --> D[변경사항 커밋]
    D --> E[origin/dev에 push]
    
    style B fill:#90EE90
    style C fill:#90EE90
    style E fill:#87CEEB
```

#### 1. dev 브랜치로 이동

```bash
git checkout dev
```

#### 2. 삭제 커밋 되돌리기 (Revert)

```bash
git revert 9142cfa --no-edit
```

**결과**: 파일이 Git 히스토리에 다시 추가됨

```
Before:  [... commits ...] → [9142cfa: DELETE] → [... commits ...]
After:   [... commits ...] → [9142cfa: DELETE] → [... commits ...] → [NEW: RESTORE]
```

#### 3. Git 추적 중지 (로컬 파일은 유지)

```bash
git rm --cached src/main/resources/application.properties
```

**주의**: `--cached` 옵션이 중요합니다!
- `--cached` 있음 ✅: Git 추적만 중지, 로컬 파일 유지
- `--cached` 없음 ❌: 파일이 실제로 삭제됨

#### 4. 변경사항 커밋

```bash
git commit -m "chore: Stop tracking application.properties"
```

#### 5. 원격 저장소에 푸시

```bash
git push origin dev
```

### 생성된 커밋

```
706c5b7  (origin/dev) Merge pull request #17
   ↓
9142cfa  Delete application.properties  ← 문제의 커밋
   ↓
a023275  Revert "Delete application.properties"  ← Step 2에서 생성
   ↓
d358606  chore: Stop tracking application.properties  ← Step 4에서 생성
```

---

## ✨ 검증

### 테스트 시나리오

```bash
# 1. 다른 브랜치로 이동
git checkout feat/Follow,FollowingList

# 2. 파일 존재 확인
ls -la src/main/resources/application.properties
# ✅ 파일 존재

# 3. dev 브랜치로 돌아오기
git checkout dev

# 4. 파일 여전히 존재
ls -la src/main/resources/application.properties
# ✅ 파일 존재

# 5. pull 실행
git pull

# 6. 파일이 삭제되지 않음 확인
ls -la src/main/resources/application.properties
# ✅ 파일 존재 (삭제되지 않음!)
```

### Git 상태 확인

```bash
$ git status

On branch dev
Your branch is up to date with 'origin/dev'.

nothing to commit, working tree clean
```

```bash
$ git check-ignore -v application.properties

.gitignore:52:application.properties  src/main/resources/application.properties
```

---

## 👥 팀원 안내사항

### 모든 팀원이 해야 할 일

#### 1. dev 브랜치 업데이트

```bash
git checkout dev
git pull origin dev
```

#### 2. application.properties 직접 관리

각자의 로컬 환경에 맞는 `application.properties` 파일을 생성/유지하세요.

```properties
# src/main/resources/application.properties 예시

# Database Configuration
spring.datasource.url=jdbc:mysql://your-ec2-instance:3306/database
spring.datasource.username=your-username
spring.datasource.password=your-password

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://your-mongodb-url:27017/database

# Server Configuration
server.port=8080
```

#### 3. 주의사항

⚠️ **절대 하지 말아야 할 것**:
- `application.properties`를 Git에 추가하지 마세요
- 민감한 정보(비밀번호, API 키 등)를 커밋하지 마세요

✅ **해야 할 것**:
- 자신의 로컬 환경 설정을 `application.properties`에 작성
- 파일이 `.gitignore`에 포함되어 있는지 확인
- 필요시 팀원들과 설정 항목 공유 (값은 공유하지 않음)

---

## 📚 참고: Git에서 파일 추적 중지하는 방법

### 시나리오별 가이드

| 상황 | 명령어 | 결과 |
|------|--------|------|
| 파일을 Git에서 제거하고 로컬에도 삭제 | `git rm <file>` | Git 추적 중지 ✅<br>로컬 파일 삭제 ❌ |
| 파일을 Git에서만 제거 (로컬 유지) | `git rm --cached <file>` | Git 추적 중지 ✅<br>로컬 파일 유지 ✅ |
| 이미 추적 중인 파일 무시 | `.gitignore` 추가 후<br>`git rm --cached <file>` | Git 추적 중지 ✅<br>향후 무시됨 ✅ |

### 추가 설정: Git Credential 설정

팀원들이 매번 토큰을 입력하지 않으려면:

```bash
# 방법 1: Credential Helper 사용 (권장)
git config --global credential.helper osxkeychain  # macOS
git config --global credential.helper manager      # Windows

# 방법 2: Remote URL에 토큰 포함 (보안 주의)
git remote set-url origin https://USERNAME:TOKEN@github.com/chimugreen/green_server.git
```

---

## 📊 문제 해결 전후 비교

### Before (문제 상황)

```
dev 브랜치에서:
  git pull
    ↓
  삭제 커밋(9142cfa) 적용
    ↓
  ❌ application.properties 파일 삭제됨
    ↓
  😱 EC2 설정 날아감
```

### After (해결 완료)

```
dev 브랜치에서:
  git pull
    ↓
  파일이 Git에서 추적되지 않음
    ↓
  ✅ application.properties 파일 유지됨
    ↓
  😊 안전하게 작업 가능
```

---

## 🎯 핵심 포인트

1. **`.gitignore`는 이미 추적 중인 파일에는 적용되지 않습니다**
2. **민감한 정보가 포함된 파일은 절대 Git에 커밋하지 마세요**
3. **파일을 삭제하는 커밋이 있으면 pull 시 로컬 파일도 삭제됩니다**
4. **해결책: revert → git rm --cached → 커밋 순서로 진행**

---

## 📝 작성 정보

- **작성일**: 2025-11-19
- **해결 브랜치**: `dev`
- **관련 커밋**: 
  - 문제 커밋: `9142cfa`
  - 해결 커밋: `a023275`, `d358606`
- **작성자**: Kang Hyeonjun
