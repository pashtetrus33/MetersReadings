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
        <#list records as record>
                <tr>
                    <th>${record.dateOfCreated}</th>
                    <th>${record.user.name}</th>
                    <th>${record.user.address}</th>
                    <th>${record.kitchenCold}</th>
                    <th>${record.kitchenHot}</th>
                    <th>${record.toiletCold}</th>
                    <th>${record.toiletHot}</th>
                </tr>
        </#list>
    </table>
</@t.template>