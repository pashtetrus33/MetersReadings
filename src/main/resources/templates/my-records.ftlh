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
    <div class="card-columns">
        <#list records as record>
            <a href="/record/${record.id}" class="record-link">
                <div class="card text-white bg-dark my-3 record">

                    <div class="card-body">
                        <p class="card-text"> Кухня (холодная вода): ${record.kitchenCold}</p>
                        <p class="card-text"> Кухня (горячая вода): ${record.kitchenHot}</p>
                        <p class="card-text"> Cанузел (холодная вода): ${record.toiletCold}</p>
                        <p class="card-text"> Cанузел (горячая вода): ${record.toiletHot}</p>
                    </div>
                    <div class="card-footer">
                        ${record.dateOfCreated}
                    </div>
                </div>
            </a>
        <#else>
            <h3 style="text-align: center">Не найдено</h3>
        </#list>
    </div>
</@t.template>