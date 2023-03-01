<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h1 style="text-align: center">Панель администратора</h1><hr>

    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       href="/registration" role="button">
        Регистрация нового пользователя
    </a>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       href="/profile" role="button">
        Назад на страницу профиля
    </a>
    <br>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Login</th>
            <th scope="col">Адрес</th>
            <th scope="col">Активность</th>
            <th scope="col">Роли</th>
            <th scope="col">Редактирование</th>
            <th scope="col">Подробная информация</th>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.login}</th>
                <th>${user.address}</th>
                <th><#if user.active>true<#else>false</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th><a href="/admin/user/edit/${user.id}">Редактировние</a></th>
                <th><a href="/user/${user.id}">Подробная информация</a></th>
            </tr>
        <#else>
            <h3>Пользователей нет</h3>
        </#list>
    </table>
</@t.template>