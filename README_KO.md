# Create Mob Masher

**Create 동력을 처먹고 몹 시체와 전리품을 뱉어내는 살벌한 쇳덩어리.**

Create Mob Masher는 [Create](https://github.com/Creators-of-Create/Create)의 회전 동력과
[Mob Grinding Utils](https://www.curseforge.com/minecraft/mc-mods/mob-grinding-utils)의
몹 분쇄 기능을 한데 처박은 NeoForge 애드온이다.

RPM을 충분히 공급하고 MGU 톱 업그레이드를 쑤셔 넣으면, MGU 가짜 플레이어가 Null Sword를
들고 기계 안에 들어온 생명체들을 주기적으로 죽도록 후려갈긴다.

[English README](README.md)

## 요구 사항

- Minecraft 1.21.1
- Java 21
- NeoForge 21.1.233 이상
- Create 6.0.10 이상, 6.1.0 미만
- Mob Grinding Utils 1.1.10 이상

Create와 Mob Grinding Utils는 필수 의존성이다. 둘 중 하나라도 빼놓고 이 애드온만
던져 넣으면 당연히 곱게 돌아갈 리가 없다.

## 이 새끼가 하는 일

- 블록 아래쪽 축으로 Create 회전 동력을 받는다.
- **128 RPM** 미만이면 꿈쩍도 안 하는 비싼 고철이 된다.
- **16 SU/RPM**의 부하를 먹는다.
- 대략 한 블록 크기의 작동 범위 안에 있는 모든 생명체를 공격한다.
- 128 RPM에서는 30틱마다, 256 RPM 이상에서는 10틱마다 한 번씩 작동한다.
- MGU 가짜 플레이어와 Null Sword로 직접 공격해서 플레이어 처치처럼 처리된다.
- Mob Grinding Utils의 톱 업그레이드를 내부에 저장하고 공격에 적용한다.
- 우클릭으로 제거하거나 블록을 부수면 장착된 업그레이드를 전부 돌려준다.
- Create 고글로 작동 상태, 필요 속도, 부하와 장착된 업그레이드를 확인할 수 있다.
- 동력 공급부터 몹을 갈아버리는 장면까지 보여주는 Create Ponder를 제공한다.

## 사용법

1. Mechanical Mob Masher를 설치한다.
2. 아래쪽 면의 축에 Create 회전 동력을 연결한다.
3. 최소 128 RPM을 공급한다. 모자라면 이 고철덩이는 아무것도 안 한다.
4. 몹을 기계의 작동 범위 안으로 밀어 넣는다.
5. 원하는 장치로 전리품을 회수한다.

인벤토리에서 아이템에 커서를 올리고 Create Ponder 키(기본값 `W`)를 누르면 설치법,
필요 동력, 업그레이드 장착과 제거, 몹 분쇄 과정을 한 번에 볼 수 있다.

## 업그레이드

MGU 톱 업그레이드를 들고 기계에 우클릭하면 해당 종류의 내부 슬롯에 하나씩 처넣는다.
종류별 최대 장착 개수는 Mob Grinding Utils 서버 설정을 따른다.

적용되는 업그레이드 효과:

- 날카로움
- 약탈
- 발화
- 강타
- 살충
- 참수

빈손으로 웅크린 채 우클릭하면 내부 슬롯 역순으로 업그레이드를 하나씩 도로 뜯어낸다.
기계를 부수면 장착된 업그레이드를 전부 바닥에 토해내므로 비싼 강화 부품이 증발할
걱정은 안 해도 된다.

## 조합법

이 모드는 **기본 조합법을 의도적으로 제공하지 않는다.** 모드팩 제작자가 데이터팩,
KubeJS, CraftTweaker 같은 수단으로 자기 팩 밸런스에 맞는 비용을 알아서 처박으라는
설계다. 빼먹은 기능이 아니니 조합법 어디 갔냐고 저장소를 뒤집어엎지 말자.

개발이나 테스트 또는 다른 방식으로 기계를 지급하는 팩에서는 다음 명령어를 쓰면 된다.

```mcfunction
/give @s createmobmasher:mechanical_mob_masher
```

## 알아둘 점

- 범위 안의 모든 `LivingEntity`가 공격 대상이다. 플레이어든 아군이든 적군이든 가리지
  않으니 기계 안에 기어들어 갔다가 얻어맞고 억울한 척하지 말자.
- 현재는 초기 공개 버전이다. 사용자들이 기상천외하게 터뜨린 버그는
  [GitHub 이슈 트래커](https://github.com/MoonScenty/CreateMobMasher/issues)에 신고하면 된다.

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

Gradle 의존성 캐시가 개판 났다면:

```powershell
.\gradlew.bat --refresh-dependencies
```

## 라이선스

이 프로젝트는 [MIT 라이선스](LICENSE)로 배포한다. 마음대로 뜯고 고치고 재배포해서
별의별 흉악한 물건을 만들어도 된다. 대신 저작권 및 라이선스 고지는 그대로 남겨라.
