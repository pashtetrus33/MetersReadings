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
    <#if user.isAdmin() || user.isBuh()>
        <div style="margin: 0 auto; text-align: center">
            <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
               href="/export-to-excel" role="button">
                Выгрузка в Excel
            </a>
        </div>
    </#if>
    <br>
    <h5 style="text-align: center">Счетчики воды</h5>
    <h5 style="">Месяц, год: ${currentMonth} ${currentYear?c}</h5>
    <table class="table table-hover table-dark" style="background-color: #03305c">

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
                <th><#if (user.areRecords()) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().dateOfCreatedString}<#else>--</#if></th>
                <th>${user.name}</th>
                <th>${user.address}</th>
                <th><#if (user.areRecords()) && (user.getLastRecord().kitchenCold != 0) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().kitchenCold}<#else>--</#if></th>
                <th><#if (user.areRecords()) && (user.getLastRecord().kitchenHot != 0) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().kitchenHot}<#else>--</#if></th>
                <th><#if (user.areRecords()) && (user.getLastRecord().toiletCold != 0) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().toiletCold}<#else>--</#if></th>
                <th><#if (user.areRecords()) && (user.getLastRecord().toiletHot != 0) && (user.getLastRecord().dateOfCreated.month.equals(currentMonth))>${user.getLastRecord().toiletHot}<#else>--</#if></th>
            </tr>
        <#else>
            <h4>Пользователей нет</h4>
        </#list>
    </table>

    <hr>
    <h5 style="text-align: center">Счетчики электричества</h5>
    <h5 style="text-align: center">Месяц, год: ${currentMonth} ${currentYear?c}</h5>
    <table class="table table-hover table-dark" style="background-color: #03305c">

        <tr>
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Имя</th>
            <th scope="col" style="vertical-align: middle">Адрес</th>
            <th scope="col" style="vertical-align: middle">Электричество</th>
            <th scope="col" style="vertical-align: middle">Удалить</th>
        </tr>
        <#list electricityRecords as record >
            <tr>
                <th style="vertical-align: middle">${record.getDateOfCreatedString()}</th>
                <th>${record.getUser().getName()}</th>
                <th>${record.getUser().getAddress()}</th>
                <th style="vertical-align: middle">${record.electricity}</th>
                <th>
                    <form action="/eletricityrecord/delete/${record.user.id?c}/${record.id?c}" method="post"
                          style="margin: 0 auto; text-align: center">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" style="text-align: center" class="btn btn-danger mb-2">
                            Удалить запись
                        </button>
                    </form>
            </tr>
        </#list>
    </table>
</@t.template>