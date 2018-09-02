# git 명령어


### 전역 설정 정보 조회
>  git config - -global - -list

### 전역 설정 정보 삭제시키기
> git config --global --unset-all user.email

> git config --global --unset-all user.name




### 새로운 저장소 초기화하기
> git init

### 저장소 복제하기
>  git clone <저장소 url>

### 새로운 원격 저장소 추가하기
>  git remote add <원격 저장소> <저장소 url>

### 새로운 파일 git에 등록시키기(staging)
> git add <파일>

### 현재까지 작업한 내용 저장하기

> git commit -m “<메시지>”


### 원격 저장소에서 합치지 않고 지역 브랜치로 변경 사항 가져오기
> git fetch <원격 저장소>

### 원격 저장소에서 변경 사항을 가져와 현재 브랜치에 합치기
> git pull <원격 저장소>

### 새로운 로컬 브랜치를 원격 저장소에 푸싱하기
> git push <원격 저장소> <지역 브랜치>
