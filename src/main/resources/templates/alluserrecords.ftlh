<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h1 style="text-align: center">Показания пользователей</h1><hr>

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
            <th scope="col">Кухня<br> (холодная вода)</th>
            <th scope="col">Кухня<br> (горячая вода)</th>
            <th scope="col">Ванная<br> (холодная вода</th>
            <th scope="col">Ванная<br> (горячая вода)</th>
        </tr>
        <#list users as user>
            <tr>
                <th><#if user.areRecords()>${user.getLastRecord().dateOfCreated}<#else> - </#if></th>
                <th>${user.name}</th>
                <th>${user.address}</th>
                <th><#if user.areRecords()>${user.getLastRecord().kitchenCold}<#else> - </#if></th>
                <th><#if user.areRecords()>${user.getLastRecord().kitchenHot}<#else> - </#if></th>
                <th><#if user.areRecords()>${user.getLastRecord().toiletCold}<#else> - </#if></th>
                <th><#if user.areRecords()>${user.getLastRecord().toiletHot}<#else> - </#if></th>

            </tr>
        <#else>
            <h3>Пользователей нет</h3>
        </#list>
    </table>
</@t.template>