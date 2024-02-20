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


    <table class="table table-hover table-dark" style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
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
        <#list records as meterReading>
            <tr>
                <th style="vertical-align: middle">${meterReading.dateOfCreatedString}</th>
                <th style="vertical-align: middle"><#if meterReading.kitchenCold != 0>${meterReading.kitchenCold}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.kitchenHot != 0>${meterReading.kitchenHot}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.toiletCold != 0>${meterReading.toiletCold}<#else>--</#if></th>
                <th style="vertical-align: middle"><#if meterReading.toiletHot != 0>${meterReading.toiletHot}<#else>--</#if></th>
            </tr>
        </#list>
        </tbody>
    </table>

    <br>

    <table class="table table-hover table-dark" style="width:auto;word-break: break-all;table-layout: fixed; margin: auto; background-color: #03305c">
        <thead>
        <tr>
            <th scope="col" style="vertical-align: middle">Дата (электричество)</th>
            <th scope="col" style="vertical-align: middle">Электричество</th>
            <th scope="col" style="vertical-align: middle">Данные внес</th>
        </tr>
        </thead>
        <tbody>
        <#list electricityRecords as electricityRecord>
            <tr>
                <th style="vertical-align: middle">${electricityRecord.getDateOfCreatedString()}</th>
                <th style="vertical-align: middle"><#if electricityRecord.electricity != 0>${electricityRecord.electricity}<#else>--</#if></th>
                <th style="vertical-align: middle">${electricityRecord.dataProviderName}</th>
            </tr>
        </#list>
        </tbody>
    </table>
</@t.template>