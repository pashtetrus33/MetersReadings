<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Показания пользователей</h4>
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
            <th scope="col">Кухня<br> (холодная вода)</th>
            <th scope="col">Кухня<br> (горячая вода)</th>
            <th scope="col">Ванная<br> (холодная вода</th>
            <th scope="col">Ванная<br> (горячая вода)</th>
        </tr>
        <#list users as user>
            <#if user.areRecords()>
                <tr>
                    <th>${user.getLastRecord().dateOfCreated}</th>
                    <th>${user.name}</th>
                    <th>${user.address}</th>
                    <th>${user.getLastRecord().kitchenCold}</th>
                    <th>${user.getLastRecord().kitchenHot}</th>
                    <th>${user.getLastRecord().toiletCold}</th>
                    <th>${user.getLastRecord().toiletHot}</th>
                </tr>
            </#if>
        <#else>
            <h4>Пользователей нет</h4>
        </#list>
    </table>
</@t.template>