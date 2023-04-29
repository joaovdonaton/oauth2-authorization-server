# Authorization Server

<b>Aluno:</b> João Vitor Donaton <br>

### **Instruções Para Compilar:**
- Clonar repositório
- Consulte os arquivos no ```./examples/``` na raiz do repositório, e configure as propriedades solicitadas
  - Criar arquivo ```application.properties``` em ```src/java/resources```
  - Criar arquivo ```apiCredentials.properties``` em ```src/java/resources```
- Google API para OpenID:
  - Crie um projeto no GCP console
  - Configure as credenciais para OAuth
  - Adicione a uri do oidc debugger (https://oidcdebugger.com/debug) e da aplicação nas urls autorizadas
- Utilize o comando ```./mvnw spring-boot:run``` ou use o Intellij para compilar e rodar

### **Detalhes e Documentação:**

- Endpoints da API estão todos dentro do context path: ```/api/oauth/```
  - **Authorization Code Grant with PKCE**: ```/pkce/```
    - ```/authorize``` recebe o clientID e o challenge, retornar o Authorization Code
    - ```/exchange``` recebe o Authorizatio Code e o verifier, retorna o Access Code
  - **Open ID Connect Authentication** ```/oidc/```
    - ```/exchange``` recebe o Authentication Code do API
      - Obtenha o Code através do [oidc debugger](https://oidcdebugger.com/debug)
      - Escopos necessários: ```openid email profile```