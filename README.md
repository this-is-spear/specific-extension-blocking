# specific-extension-blocking

특정 확장자 차단 기능

### 시연 영상

https://user-images.githubusercontent.com/92219795/203914803-797ff298-b1dd-4e8c-b250-d007260ae7df.mov

자세한 내용은 [화면](http://52.78.120.129/index)에서 해볼 수 있고, [문서](http://52.78.120.129/docs/index.html)도 확인 가능합니다. 

### Skill

- Java
- Gradle
- Shell Script

### Framework and Library

- Jacoco
- Spring
    - Spring Web
    - Spring Data Jpa
    - Spring Rest Docs
    - Spring Validation
    - Thymeleaf
    - Lombok
    - h2
- MySQL
- GitHub Actions
- Docker
- Aws ec2
- Git

### Build

```shell
./gradlew build
```

### Boot Jar

```shell
java -jar build/libs/specific-extension-blocking-1.0.0-SNAPSHOT.jar -Dspring.profiles.active=test
```
