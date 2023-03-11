<#import "blocks/template.ftlh" as t>
<@t.template request.getUser()>
    <h4 style="text-align: center"> ${request.getUser().name}</h4>
    <h4 style="text-align: center"> ${request.dateOfCreated}</h4>
    <h4 style="text-align: center"> Заявка: ${request.description}</h4>

    <hr>
    <form action="/request/statusedit" method="post">
        <div>
            <label for="role" style="margin: auto;">Изменить статус заявки</label><br>
            <@enumStatus "key" statuses/>
        </div>
        <input type="hidden" value="${request.id}" name="requestId">
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
    <#macro enumStatus selectName enumValues>
        <label>
            <select name="${selectName}">
                <#list enumValues as enum>
                    <option value="${enum}">${enum.title}</option>
                </#list>
            </select>
        </label>
    </#macro>


</@t.template>