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
        <table class="table table-hover table-dark" style="width:100%;word-break: break-all;table-layout: fixed;background-color: #03305c">
            <tr>
                <th scope="col" style="vertical-align: middle">Статус заявки</th>
                <th scope="col" style="vertical-align: middle">Исполнитель</th>
                <th scope="col" style="vertical-align: middle">Сохранить</th>
            </tr>
            <tr>
                <th><@enumStatus "key" statuses/></th>
                <th>  <@enumStatus "executor" executors/></th>
                <th><button type="submit" class="btn btn-dark" style="margin: 10px;">Сохранить</button></th>
            </tr>
        </table>
        <input type="hidden" value="${request.id}" name="requestId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
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