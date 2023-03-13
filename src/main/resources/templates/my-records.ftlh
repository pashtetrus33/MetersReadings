<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4 style="text-align: center">История показаний</h4>
    <hr>
    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center"
           href="/profile" role="button">
            Назад
        </a>
    </div>

    <br>

    <table class="table table-hover table-dark" style="width:auto;word-break: break-all;table-layout: fixed">
        <thead>
        <tr>
            <th scope="col">Дата</th>
            <th scope="col">Кухня (холодная вода)</th>
            <th scope="col">Кухня (холодная вода)</th>
            <th scope="col">Ванная (холодная вода)</th>
            <th scope="col">Ванная (горячая вода)</th>
        </tr>
        </thead>
        <tbody>
        <#list records as record>
            <tr>
                <th>${record.dateOfCreated}</th>
                <th>${record.kitchenCold}</th>
                <th>${record.kitchenHot}</th>
                <th>${record.toiletCold}</th>
                <th>${record.toiletHot}</th>
            </tr>
        </#list>
        </tbody>
    </table>
</@t.template>