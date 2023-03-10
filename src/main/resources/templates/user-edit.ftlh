<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h3 style="text-align: center">Редактирование пользователя:</h3>
    <h3 style="text-align: center"> ${user.name}</h3>

    <hr>
    <form action="/admin/user/edit" method="post" style="text-align: center;">
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

    <form action="/admin/user/password_change" method="post" style="text-align: center">
        <label for="password">Пароль</label>
        <input type="password" class="form-control" id="password" required name="password" style="width: auto; margin: auto">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark" style="margin: 13px;">Сохранить</button>
    </form>


    <form action="/admin/user/delete" method="post" style="text-align: center;">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" style="width: auto" class="btn btn-danger mb-2">
            Удалить
        </button>
    </form>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/admin" role="button">
            Назад
        </a>
    </div>

<#-- handy macro for this -->
    <#macro enumSelect selectName enumValues>
        <select name="${selectName}">
            <#list enumValues as enum>
                <option value="${enum}">${enum.name}</option>
            </#list>
        </select>
    </#macro>

<#-- calling the macro -->
    <@enumSelect "myEnumSelectName" roles/>

</@t.template>