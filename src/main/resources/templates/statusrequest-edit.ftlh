<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h3 style="text-align: center">Редактирование заявки пользователя:</h3>
    <h3 style="text-align: center"> ${user.name}</h3>
    <h3 style="text-align: center"> Заявка: ${user.getFirstRequest().description}</h3>
    <h3 style="text-align: center"> Дата создания:  ${user.getFirstRequest().dateOfCreated}</h3>

    <hr>
    <form action="/request/statusedit" method="post">
        <#list statuses as status>
            <div>
                <label><input type="checkbox"
                              name="${status}" ${user.getFirstRequest().statuses?seq_contains(status)?string("checked", "")}>${status}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.getFirstRequest().id}" name="requestId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark" style="margin: 10px;">Сохранить</button>
    </form>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark" style="margin: 10px;"
           href="/allusersrequests" role="button">
            Назад
        </a>
    </div>

<#-- handy macro for this -->
    <#macro enumSelect selectName enumValues>
        <select name="${selectName}">
            <#list enumValues as enum>
                <option value="${enum}">${enum.title}</option>
            </#list>
        </select>
    </#macro>

<#-- calling the macro -->
    <@enumSelect "myEnumSelectName" statuses/>

</@t.template>