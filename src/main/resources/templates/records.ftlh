<#import "blocks/template.ftlh" as t>
<@t.template user>

    <div class="card-columns">
        <#list records as record>
            <a href="/record/${record.id?c}" class="record-link">
                <div class="card text-white bg-dark my-3 record">

                    <img src="/images/${record.previewImageId}"
                         class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${record.title}</h5>
                        <p class="card-text">${record.price} ₽</p>
                    </div>
                    <div class="card-footer">
                        ${record.city}
                    </div>
                </div>
            </a>
        <#else>
            <h4 style="text-align: center">Не найдено</h4>
        </#list>
    </div>
</@t.template>