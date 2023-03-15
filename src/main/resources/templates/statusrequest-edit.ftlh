<#import "blocks/template.ftlh" as t>
<@t.template request.getUser()>
    <h4 style="text-align: center"> ${request.getUser().name}</h4>
    <h4 style="text-align: center"> ${request.dateOfCreated}</h4>
    <h4 style="text-align: center; width: 100%;word-break: break-all;table-layout: fixed"> ${request.description}</h4>
    <#list request.getStatuses() as status>
        <h5 style="text-align: center">Текущий статус: ${status}</h5>
        <h5 style="text-align: center">Текущий исполнитель: ${request.executor}</h5>
    </#list>

    <hr>
    <form action="/request/statusedit" method="post">
        <div>
            <label for="role" style="margin: 10px;">Изменить статус заявки</label><br>
            <@enumStatus "key" statuses/>
        </div>
        <div>
            <label for="executor" style="margin: 10px;">Исполнитель</label><br>
            <@enumStatus "executor" executors/>
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