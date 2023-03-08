<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <form action="/" method="post">
        <div class="form-group" style="margin: 0 auto; text-align: center; width: auto">
            <label for="email" style="margin: auto;">Электронная почта</label>
            <input type="email" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email"
                   name="email">
            <div id="email" class="invalid-feedback">
                Электронная почта не найдена
            </div>
        </div>
        <div class="form-group" style="margin: 0 auto; text-align: center; width: auto">
            <label for="password" style="margin: auto;">Пароль</label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password"
                   name="password">
            <div id="password" class="invalid-feedback">
                Неверный пароль
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style=" margin: 15px auto; width: auto" class="btn btn-dark">Войти</button>
        </div>
    </form>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a style=" margin: auto; width:auto" class="btn btn-dark"
           href="/registration" role="button">
            Регистрация
        </a>
    </div>
    <#if loginmessage??>
        <h4 style="color: red">${loginmessage}</h4>
    </#if>
    <#if loginalert??>
        <h4 style="color: red">${loginalert}</h4>
    </#if>

</@t.template>