<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h1 style="text-align: center">Панель администратора</h1><hr>

    <div style="margin: 0 auto; text-align: center">
    <a class="btn btn-danger mb-2" style="text-align: center; width: auto"
       href="/registration" role="button">
        Регистрация
    </a>
    </div>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-danger mb-2" style="text-align: center; width: 30%"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Логин</th>
            <th scope="col">Эл.почта</th>
            <th scope="col">Адрес</th>
            <th scope="col">Активность</th>
            <th scope="col">Роли</th>
            <th scope="col">Редактирование</th>
            <th scope="col">Подробная информация</th>

        </tr>
        <#list users as user>
            <tr>
                <th>${user.login}</th>
                <th>${user.email}</th>
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