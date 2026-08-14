# Create Mob Masher

**Create 동력을 처먹고 몹 시체와 전리품을 뱉어내는 살벌한 쇳덩어리.**

Create Mob Masher는 [Create](https://github.com/Creators-of-Create/Create)의 회전 동력과
[Mob Grinding Utils](https://www.curseforge.com/minecraft/mc-mods/mob-grinding-utils)의
몹 분쇄 기능을 한데 처박은 NeoForge 애드온이다.

RPM을 충분히 공급하고 업그레이드를 쑤셔 넣으면, 가짜 플레이어가 MGU의 Null Sword를
들고 기계 안에 들어온 생명체들을 주기적으로 죽도록 후려갈긴다.

[English README](README.md)

## 요구 사항

- Minecraft 1.21.1
- Java 21
- NeoForge 21.1.233 이상
- Create 6.0.10 이상, 6.1.0 미만
- Mob Grinding Utils 1.1.10 이상

개발 환경에서는 Create 툴체인을 통해 Ponder, Flywheel, Registrate도 사용한다. 정확한
의존성 버전은 `gradle.properties`와 `build.gradle`을 보면 된다.

## 이 새끼가 하는 일

- 블록 아래쪽 축으로 Create 회전 동력을 받는다.
- **128 RPM** 미만이면 꿈쩍도 안 하는 비싼 고철이 된다.
- **16 SU/RPM**의 부하를 먹는다.
- 대략 한 블록 크기의 작동 범위 안에 있는 모든 생명체를 공격한다.
- 128 RPM에서는 30틱마다, 256 RPM 이상에서는 10틱마다 한 번씩 작동한다.
- 가짜 플레이어가 직접 공격해서 일반 환경 피해가 아니라 플레이어 처치처럼 처리된다.
- Mob Grinding Utils의 톱 업그레이드를 내부에 저장하고 공격에 적용한다.
- Create 고글을 쓰면 작동 상태, 필요 속도, 부하와 장착된 업그레이드를 확인할 수 있다.

## 사용법

1. Mechanical Mob Masher를 설치한다.
2. 아래쪽 면에 Create 축을 연결한다.
3. 최소 128 RPM을 공급한다. 모자라면 이 고철덩이는 아무것도 안 한다.
4. 몹을 기계의 작동 범위 안으로 밀어 넣는다.
5. 몹 대가리가 신나게 깨지는 동안 원하는 방식으로 전리품을 회수한다.

현재 저장소에는 조합법 데이터가 없다. 개발이나 테스트 중에는 다음 명령어로 블록을
꺼내 쓰면 된다.

```mcfunction
/give @s createmobmasher:mechanical_mob_masher
```

## 업그레이드

MGU 톱 업그레이드를 들고 기계에 우클릭하면 해당 종류의 내부 슬롯에 하나씩 처넣는다.
종류별 최대 장착 개수는 Mob Grinding Utils 서버 설정을 따른다.

적용되는 업그레이드 효과는 다음과 같다.

- 날카로움
- 약탈
- 발화
- 강타
- 살충
- 참수

빈손으로 웅크린 채 우클릭하면 내부 슬롯 역순으로 업그레이드를 하나씩 도로 뜯어낸다.
기계를 부수면 장착된 업그레이드를 전부 바닥에 토해내므로 비싼 강화 부품이 증발할
걱정은 안 해도 된다.

## 빌드

저장소를 복제한 뒤 다음 명령어를 실행한다.

```powershell
.\gradlew.bat build
```

완성된 JAR 파일은 `build/libs`에 생성된다.

개발용 클라이언트를 실행하려면:

```powershell
.\gradlew.bat runClient
```

Gradle 의존성 캐시가 개판 났다면 다음 명령어를 시도한다.

```powershell
.\gradlew.bat --refresh-dependencies
```

## 아직 덜 된 구석

- 조합법이 아직 없다.
- 영어 언어 파일에는 실제 블록 이름을 비롯한 현지화 항목이 제대로 안 들어가 있다.
- MGU 톱에 거는 Mixin은 현재 디버그 메시지만 찍고 실제 동작은 바꾸지 않는다.
- 범위 안의 모든 `LivingEntity`가 공격 대상이다. 필터 기능이 생기기 전까지 기계 안에
  기어들어 갔다가 얻어맞고 억울한 척하지 말자.

## 라이선스

All Rights Reserved. 자세한 내용은 [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt)를 참고한다.
