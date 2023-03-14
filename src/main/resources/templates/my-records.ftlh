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
            <th scope="col" style="vertical-align: middle">Дата</th>
            <th scope="col" style="vertical-align: middle">Кухня(хол.)</th>
            <th scope="col" style="vertical-align: middle">Кухня(гор.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(хол.)</th>
            <th scope="col" style="vertical-align: middle">Ванная(гор.)</th>
        </tr>
        </thead>
        <tbody>
        <#list records as record>
            <tr>
                <th style="vertical-align: middle">${record.dateOfCreated}</th>
                <th style="vertical-align: middle">${record.kitchenCold}</th>
                <th style="vertical-align: middle">${record.kitchenHot}</th>
                <th style="vertical-align: middle">${record.toiletCold}</th>
                <th style="vertical-align: middle">${record.toiletHot}</th>
            </tr>
        </#list>
        </tbody>
    </table>
</@t.template>