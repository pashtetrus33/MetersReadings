<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h4 style="text-align: center">Заявки на ремонт</h4>
    <hr>

    <div style="margin: 0 auto; text-align: center">
        <a class="btn btn-dark" style="margin: 10px;"
           href="/profile" role="button">
            Назад
        </a>
    </div>
    <br>
    <table class="table table-hover table-dark" style="width:100%;word-break: break-all;table-layout: fixed;background-color: #03305c">
        <thead>
        <tr>
            <th scope="col">Дата</th>
            <th scope="col">Имя</th>
            <th scope="col">Адрес</th>
            <th scope="col">Описание</th>
            <th scope="col">Статус</th>
            <th scope="col">Исполнитель</th>
            <th scope="col">Фото</th>
            <th scope="col">Удаление</th>

        </tr>
        </thead>
        <tbody>
        <#list requests as request>
                        <tr>
                            <th>${request.dateOfCreatedString}</th>
                            <th>${request.user.name}</th>
                            <th>${request.user.address}</th>
                            <th>${request.description}</th>
                            <th><a href="/request/statusedit/${request.id?c}" style="color: #5feb08">${request.status}</a></th>
                            <th>${request.executor}</th>
                            <th><#list request.getImages() as image><a href="/images/${image.id}">
                                Ссылка</a></#list></th>
                            <th>  <form action="/request/delete/${request.id?c}" method="post" style="margin: 0 auto; text-align: center">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-danger mb-2">
                                        Удалить
                                    </button>
                                </form>
                            </th>
                        </tr>
                </#list>
        </tbody>
    </table>
</@t.template>