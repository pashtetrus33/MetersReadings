<#import "blocks/template.ftlh" as t>
<@t.template request.getUser()>
    <h4 style="text-align: center"> ${request.getUser().name}</h4>
    <h4 style="text-align: center"> ${request.dateOfCreated}</h4>
    <h4 style="text-align: center; width: 100%;word-break: break-all;table-layout: fixed"> ${request.description}</h4>
    <#list request.getStatuses() as status>
        <h3 style="text-align: center">Текущий статус: ${status}</h3>
    </#list>

    <hr>
    <form action="/request/statusedit" method="post">
        <div>
            <label for="role" style="margin: 10px;">Изменить статус заявки</label><br>
            <@enumStatus "key" statuses/>
        </div>
        <input type="hidden" value="${request.id}" name="requestId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark" style="margin: 10px;">Сохранить</button>
    </form>
    <div class="form-group">
        <label for="address" style="margin: auto;">Исполнитель</label>
        <select id="executor" required name="executor" class="form-control">
            <option value="">Здание не выбрано</option>
            <option value="1">Орлов А.</option>
            <option value="2">Жуков Д.</option>
            <option value="3">Шаяхметов М.</option>
            <option value="4">Глушков А.</option>
        </select>
    </div>

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