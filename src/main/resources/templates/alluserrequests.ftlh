<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Заявки на ремонт</h4>
    <hr>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 25%"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Дата</th>
            <th scope="col">Имя</th>
            <th scope="col">Адрес</th>
            <th scope="col">Описание</th>
            <th scope="col">Статус</th>

        </tr>
        <#list users as user>
            <#if user.areRequests()>
                <tr>
                    <th>${user.getFirstRequest().dateOfCreated}</th>
                    <th>${user.name}</th>
                    <th>${user.address}</th>
                    <th>${user.getFirstRequest().description}</th>
                    <th><#list user.getFirstRequest().statuses as status><a href="/request/statusedit/${user.id}">${status}</a></#list></th>
                </tr>
            </#if>

        <#else>
            <h4>Пользователей нет</h4>
        </#list>
    </table>
</@t.template>