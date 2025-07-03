## XSS 보안 대응 관련 문서 

- "\<script><\/script>" 와 같은 태그는 HTML 에서 그대로 사용된다면 스크립트가 실행되어서 `공격이 실행될 수 있음`
- 그래서 `XSS (Cross-Site Scripting)` 공격이라고 함
- 일반적으로 "\<script>alert(1)<\/script>" 과 같은 문자열을 입력하면, 해당 문자열이 HTML 에서 스크립트로 실행되어서 `경고창이 뜨는 것`을 볼 수 있음

> 다만 요새 많은 Tool, Framework, Library 에서는 자동으로 문자열을 Escape 처리하여 실행되지 않도록 함.
> - 예를 들어, Spring Boot 에서는 `Thymeleaf` 템플릿 엔진을 사용하면 자동으로 Escape 처리됨
>   - \<h3 th:text="${value}"><\/h3> -> 이 경우, value 값이 "\<script>alert(1)<\/script>" 라면, HTML 에서 Escape 처리되어서 \<h3> 태그 안에 문자열로 표시됨
>     - 태그 안에 문자열만 표시된다
>   - 다만 th:utext 를 사용하게 된다면 Escape 처리가 되지 않음 -> 즉 HTML 태그로 인식되어 실행 됨 

<br>

```html
<!-- XSS 불가 -->
    <h3 th:text="${value}"></h3>

    <!-- XSS 가능(특수문자 escape 처리함) -->
    <h3 th:utext="${value}"></h3>
```
- 위와 같을때, `view/value=<h1>XSS Test</h1>` 라고 한다면, 위의것은 그대로 h3, 아래내용은 h1 태그로 렌더링 됨



<br>

---

### XSS 대응 방법 2가지
- **Escape 처리**
  - 모든것을 문자열로 출력함. -> 실행하지 않음
  - 태그나 괄호 같은것을 "&lt; &gt; &amp;" 와 같은 문자열로 변환하여 HTML 에서 실행되지 않도록 함
    - 이 경우 `템플릿 엔진` 에서는 HTML 로 렌더링 하는것이 아닌, 문자열로 취급한다.

- **Sanitize 처리**
  - 문자열을 필터링 하여 `특정 태그만 허용`, `나머지는 제거`하는 방식임
  - 예를들어, `<script>, <iframe>, onError` 와 같은 태그는 제거하고, \<h1> 태그는 허용하는 방식
    - 커뮤니티 댓글이나 게시판 글 작성시 사용될 수 있음

> 즉 `Escape` 는 문자열로 보여주고, `Sanitize` 는 특정 태그만 허용하는 방식!

---

### Flow 및 사용 예시 및 대응
- 댓글 시스템
  - b, i, u, a 정도만 허용
  - `<script>, <img onerror=...>, <iframe>` 은 제거
  - 프론트에서 dangerouslySetInnerHTML 또는 v-html 사용

<br>

- 게시판 글 작성 (CKEditor, TinyMCE 등)
  - `<h1>, \<h2>, \<p>, \<a>` 정도만 허용
  - `<script>, <iframe>` 제거
  - 출력시 정제된 HTML 출력
  - 이경우 Server 에서 Sanitize 처리 후 DB 저장

---
### Spring 에서 대응
- XSS 대응을 하기위한 XSS 필터 라이브러리들이 존재함
  - Lucy(Naver), AntiSamy(OWASP), Jsoup 등 
  - 해당 라이브러리들을 사용하여 Filter 단에서 처리할 수 있음
> 다만 @RequestParam, @RequestBody 의 내용이 자동으로 처리되지 않으므로, 구현해야함 ! 
> - Servlet Filter 기반인 HttpServletRequestWrapper 는 @RequestParam, getParameter() 만을 처리할 수 있음

--- 

### TODO: CKEditor 와 같은걸로 HTML + SCript 입력받아, 사용자의 페이지에 보여주는 방식에 대해서 어떻게 처리할지 고민해봐야함. 






