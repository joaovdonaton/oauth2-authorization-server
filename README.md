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
- Microsoft API para OpenID:
  - Registre um aplicativo no Portal Microsoft Azure
  - Na aba de "Autenticação" habilite a opção "Tokens de ID (usados para fluxos implícitos e híbridos)", configure a uri de redirect para o oidc debugger, e também configure os "Tipos de conta com suporte" para contas pessoais Microsoft.
- Utilize o comando ```./mvnw spring-boot:run``` ou use o Intellij para compilar e rodar

### Modo de Uso:
- Usar o [oidc debugger](https://oidcdebugger.com/debug) para obter Authorization Code (google) de uma conta. **OU** para obter o idToken diretamente com implicit flow (microsoft).
- Enviar Request para ```/oidc/exchange``` com o auth code do Google **OU** enviar request para ```/oidc/validate``` para validar idToken da api da Microsoft
  - Persiste o usuário e suas informações na base de dados e retorna o clientId do user
- Obter Access Token com o clientId através de ```/pkce/``` (**Authorization Code Grant With PKCE**) ou ```/codegrant/``` (**Authorization Code Grant**)
  - As rotas ```/authorize``` desses endpoints validam o clientId e retornam o Auth Code (Como se estivessem simulando a tela de login)
  - As rotas ```/exchange``` validam o verifier (PKCE) ou validam o appId e appSecret (Code Grant normal)
    - appId e appSecret validos estão em um map no ```CodeGrantAuthService.java```
- Após obter um Access Token válido. É possível acessar um endpoint restrito em ```/secret```
  - Enviar token no header "Authorization" no formato "Bearer [TOKEN]"
  - O Endpoint valida o Token e retorna o id do usuário atualmente autenticado
  - (Requisito bônus)

### **Detalhes e Documentação:**
- Swagger disponível em ```/api/oauth/swagger-ui/index.html``` para testes
- Autenticação OpenID pode ser feita através do Google ou Microsoft.
- Dados do usuário são persistidos em um banco em mememória (H2), após login com openID
- Tokens gerados pelo servidor para autorização também são persistidos em memória
- O servidor automaticamente cria um usuário de testes (```Bootstrap.java```). O clienteId do test user é printado no final do log de inicialização
- **Endpoints da API estão todos dentro do context path:** ```/api/oauth/```
  - **Authorization Code Grant with PKCE**: ```/pkce/```
    - ```/authorize``` recebe o clientID e o challenge, retorna o Authorization Code
    - ```/exchange``` recebe o Authorizatio Code e o verifier, retorna o Access Code
  - **Authorization Code Grant**: ```/codegrant/```
    - ```/authorize``` recebe o clientID, retorna o Authorization Code
    - ```/exchange``` recebe o Authorizatio Code, o AppId e o AppSecret, retorna Access Code
  - **Open ID Connect Authentication** ```/oidc/```
    - ```/exchange``` recebe o Authentication Code do Google
      - Obtenha o Code através do [oidc debugger](https://oidcdebugger.com/debug) na url https://accounts.google.com/o/oauth2/v2/auth
      - Escopos necessários: ```openid email profile```
    - ```/validate``` recebe o idToken (Microsoft)
      - Mesmos do exchange, com url https://login.microsoftonline.com/commons/oauth2/v2.0/authorize
  - **Endpoint restrito** ```/secret/```
    - Detalhado na seção "Modo de Uso"
     
