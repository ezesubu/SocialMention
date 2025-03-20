# Documentación de la Aplicación Social Mention

## 1. Descripción
Social Mention es una aplicación desarrollada en Micronaut para analizar menciones en redes sociales. Expone endpoints para evaluar el riesgo de publicaciones en Facebook y Twitter.

## 2. Tecnologías Utilizadas
- **Micronaut** como framework backend.
- **Java** como lenguaje de programación.
- **JUnit** para pruebas unitarias.
- **PostgreSQL** (pendiente de implementación, actualmente es un dummy).
- **Postman y cURL** para pruebas manuales.

## 3. Estructura del Proyecto
```
SocialMention/
│-- src/main/java/com/example/
│   ├── controllers/
│   │   ├── SocialMentionController.java
│   │   ├── SocialMentionRefactorController.java
│   ├── dtos/
│   │   ├── SocialMention.java
│   ├── services/
│   │   ├── SocialMentionService.java
│   │   ├── FacebookAnalyzerService.java
│   │   ├── TweeterAnalyzerService.java
│   │   ├── DBService.java (Dummy)
│   ├── Application.java
│-- src/test/java/com/example/controllers/
│   ├── SocialMentionRefactorControllerTest.java
│-- resources/
│   ├── application.properties
│   ├── logback.xml
```

## 4. Endpoints Disponibles
### SocialMentionController
- **POST `/AnalyzeSocialMention`**
  - Analiza el nivel de riesgo de una publicación en redes sociales.
  - **Entrada:**
    ```json
    {
      "message": "Test post",
      "facebookAccount": "user123",
      "tweeterAccount": "user_twitter",
      "tweeterUrl": "https://twitter.com/post/1",
      "facebookComments": ["Nice post!", "I disagree."]
    }
    ```
  - **Salida Esperada:**
    ```json
    "HIGH_RISK"
    ```

### SocialMentionRefactorController
- **POST `/social-mention/analyze`**
  - Endpoint refactorizado para análisis de riesgo.
  - **Entrada de ejemplo:**
    ```json
    {
      "message": "This is a test message for risk analysis",
      "facebookAccount": "user123",
      "tweeterAccount": "user_twitter",
      "tweeterUrl": "https://twitter.com/post/5",
      "facebookComments": ["This is a neutral comment", "I have no strong opinion on this post."]
    }
    ```
  - **Salida Esperada:**
    ```json
    "MEDIUM_RISK"
    ```

## 5. Cómo Hacer Pruebas en Postman y cURL
Para probar el servicio, puedes usar **Postman** o ejecutar el siguiente comando en **cURL**:

```bash
curl -X POST "http://localhost:8080/social-mention/analyze" \
-H "Content-Type: application/json" \
-d '{ "message": "Test post", "facebookAccount": "user123", "tweeterAccount": "user_twitter", "tweeterUrl": "https://twitter.com/post/1", "facebookComments": ["Nice post!", "I disagree."] }'
```

### Salida Esperada:
```
HIGH_RISK
```

Otros casos de prueba:

#### Caso de prueba HIGH_RISK:
```bash
curl -X POST "http://localhost:8080/social-mention/analyze" \
-H "Content-Type: application/json" \
-d '{ "message": "This post contains spam", "facebookAccount": "user123", "tweeterAccount": "user_twitter", "tweeterUrl": "https://twitter.com/post/2", "facebookComments": ["Spam detected!"] }'
```
**Salida esperada:**
```
HIGH_RISK
```

#### Caso de prueba LOW_RISK:
```bash
curl -X POST "http://localhost:8080/social-mention/analyze" \
-H "Content-Type: application/json" \
-d '{ "message": "This is a positive post", "facebookAccount": "user123", "tweeterAccount": "user_twitter", "tweeterUrl": "https://twitter.com/post/3", "facebookComments": ["This is an amazing and thoughtful post that brings a lot of positivity. It is very insightful and provides valuable perspectives on important matters. Truly a well-written and engaging post that deserves appreciation. This comment contains more than fifty words to test the condition."] }'
```
**Salida esperada:**
```
LOW_RISK
```

## 6. Pruebas Unitarias
Se han desarrollado pruebas unitarias con JUnit para validar el comportamiento del servicio.

Ejemplo de pruebas:
```java
@Test
void testAnalyze_HighRisk() {
    SocialMention socialMention = new SocialMention("Test post spam", "user123", "user_twitter", "https://twitter.com/post/1", new String[]{"Nice con un"});
    HttpRequest<SocialMention> request = HttpRequest.POST("/social-mention/analyze", socialMention);
    HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
    Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    Assertions.assertEquals("HIGH_RISK", response.body());
}
```

Puedes ejecutar las pruebas con:
```bash
./gradlew test
```

## 7. Futuras Mejoras
- Implementar integración con PostgreSQL para almacenar resultados de análisis.
- Mejorar los criterios de análisis de riesgo.
- Implementar autenticación y autorización.

---
