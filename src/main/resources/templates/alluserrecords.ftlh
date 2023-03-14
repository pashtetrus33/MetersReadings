<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Показания пользователей</h4>
    <hr>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <h5 style="">${currentMonth} ${currentYear}</h5>
    <table class="table table-hover table-dark">

        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Имя</th>
            <th scope="col" style="vertical-align: middle">Адрес</th>
            <th scope="col" style="vertical-align: middle">Кухня<br>холодная вода</th>
            <th scope="col" style="vertical-align: middle">Кухня<br>горячая вода</th>
            <th scope="col" style="vertical-align: middle">Ванная<br>холодная вода</th>
            <th scope="col" style="vertical-align: middle">Ванная<br>горячая вода</th>
        </tr>
        <#list users as user >
                <tr>
                    <th><#if (user.areRecords()) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().dateOfCreated}<#else>--</#if></th>
                    <th>${user.name}</th>
                    <th>${user.address}</th>
                    <th><#if (user.areRecords()) && (user.getLastRecord().kitchenCold != 0) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().kitchenCold}<#else>--</#if></th>
                    <th><#if (user.areRecords()) && (user.getLastRecord().kitchenHot != 0)&& (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().kitchenHot}<#else>--</#if></th>
                    <th><#if (user.areRecords()) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().toiletCold}<#else>--</#if></th>
                    <th><#if (user.areRecords()) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().toiletHot}<#else>--</#if></th>
                </tr>
        <#else>
            <h4>Пользователей нет</h4>
        </#list>
    </table>
</@t.template>