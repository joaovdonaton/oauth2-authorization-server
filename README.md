# Authorization Server

<b>Aluno:</b> João Vitor Donaton <br>

### **Instruções Para Compilar:**
- Clonar repositório
- Utilize o comando ```./mvnw spring-boot:run``` ou use o Intellij para compilar e rodar

### **Detalhes e Documentação:**

- Endpoints da API estão todos dentro do context path: ```/api/oauth/```
  - **Authorization Code Grant with PKCE**: ```/pkce/```
    - ```/authorize``` recebe o clientID e o challenge, retornar o Authorization Code
    - ```/exchange``` recebe o Authorizatio Code e o verifier, retorna o Access Code