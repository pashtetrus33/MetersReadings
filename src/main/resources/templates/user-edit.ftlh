<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h2 style="text-align: center">Редактирование пользователя:</h2>
    <h3 style="text-align: center"> ${user.name}</h3>

    <hr>
    <form action="/admin/user/edit" method="post">
        <#list roles as role>
            <div>
                <label><input type="checkbox"
                              name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark">Сохранить</button>
    </form>

    <form action="/admin/user/password_change" method="post">
        <label for="password">Пароль</label>
        <input type="password" class="form-control" id="password" required name="password" style="width: 25%">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark">Сохранить</button>
    </form>

</@t.template>